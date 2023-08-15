package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import com.pjt.petmily.domain.user.follow.dto.RecommendedUserDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping("/follow/{userEmail}")
    @Operation(summary = "팔로우", description = "path : 팔로우할 유저, body : 팔로우 하는 사람(현재 사용자)")
    public ResponseEntity<String> followUser(@PathVariable String userEmail, @RequestBody FollowUserDto followUserDto) {
        try {
            String message = followService.followUser(userEmail, followUserDto);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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
