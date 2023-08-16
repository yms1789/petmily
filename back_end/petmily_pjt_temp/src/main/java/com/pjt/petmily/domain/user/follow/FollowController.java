package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import com.pjt.petmily.domain.user.follow.dto.RecommendedUserDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.FCM.FirebaseCloudMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;


    @PostMapping("/follow/{userEmail}")
    @Operation(summary = "팔로우", description = "path : 팔로우할 유저, body : 팔로우 하는 사람(현재 사용자)")
    public ResponseEntity<String> followUser(@PathVariable String userEmail, @RequestBody FollowUserDto followUserDto) {
        try {
            String message = followService.followUser(userEmail, followUserDto);

            // 1. 팔로우 대상자 (userEmail에 해당하는 사용자) 찾기
            User targetUser = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없음: " + userEmail));
            System.out.println("사용자 찾기 완료");

            // 2. 팔로우 대상자의 FCM 토큰 얻기
            String fcmToken = targetUser.getFcmToken();
            System.out.println("FCM 토큰 얻기 완료: " + fcmToken);

            // 3. 메시지 전송
            if (fcmToken != null && !fcmToken.isEmpty()) {
                String title = "새 팔로워 알림";
                User followerUser = userRepository.findByUserEmail(followUserDto.getUserEmail())
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없음: " + followUserDto.getUserEmail()));
                String followerNick = followerUser.getUserNickname();
                String body = followerNick + "님이 당신을 팔로우했습니다!";
                System.out.println("파이어베이스 전송");
                firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, followUserDto.getUserEmail());
            }
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/follow/{userEmail}")
    @Operation(summary = "언팔로우", description = "해당 유저 언팔로우")
    public ResponseEntity<String> unfollowUser(@PathVariable String userEmail, @RequestBody FollowUserDto followUserDto) {
        try {
            String message = followService.unfollowUser(userEmail, followUserDto);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/follow/recommend/{currentUserEmail}")
    @Operation(summary = "팔로우 추천")
    public ResponseEntity<List<RecommendedUserDto>> recommendFollow(@PathVariable String currentUserEmail) {
        List<RecommendedUserDto> recommendedUsers = followService.getRecommendedUsers(currentUserEmail);
        return new ResponseEntity<>(recommendedUsers, HttpStatus.OK);
    }

}
