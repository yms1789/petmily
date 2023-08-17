package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.noti.entity.Noti;
import com.pjt.petmily.domain.noti.entity.NotiType;
import com.pjt.petmily.domain.noti.repository.NotiRepository;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import com.pjt.petmily.domain.user.follow.dto.RecommendedUserDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private NotiRepository notiRepository;

    @Transactional
    public String followUser(String userEmail, FollowUserDto followUserDto) {
        Optional<User> currentUserOptional = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if (currentUserOptional.isPresent()) {
            User user = currentUserOptional.get();
            Optional<User> targetUserOptional = userRepository.findByUserEmail(userEmail);
            if (targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                // 이미 팔로우 중인지 확인
                Optional<Follow> existingFollow = followRepository.findByFollowerAndFollowing(user, targetUser);
                if (existingFollow.isPresent()) {
                    throw new RuntimeException("이미 팔로우 중인 사용자입니다.");
                }

                Follow follow = Follow.builder()
                        .follower(user)
                        .following(targetUser)
                        .build();

                // 알림 생성 및 저장
                Noti noti = Noti.builder()
                        .notiType(NotiType.FOLLOW)
                        .fromUser(user)
                        .toUser(targetUser)
                        .createDate(LocalDateTime.now())
                        .isChecked(false)
                        .build();

                notiRepository.save(noti);
                followRepository.save(follow);
                return "팔로우 성공";
            } else {
                throw new RuntimeException("사용자 찾을 수 없음");
            }
        } else {
            throw new RuntimeException("현재 사용자 찾을 수 없음");
        }
    }

    @Transactional
    public String unfollowUser(String userEmail, FollowUserDto followUserDto) {
        Optional<User> currentUserOptional = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if(currentUserOptional.isPresent()) {
            User user = currentUserOptional.get();
            Optional<User> targetUserOptional = userRepository.findByUserEmail(userEmail);
            if(targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                Optional<Follow> followOptional = followRepository.findByFollowerAndFollowing(user, targetUser);
                if(followOptional.isPresent()) {
                    Follow follow = followOptional.get();

                    followRepository.delete(follow);

                    return "언팔로우 성공";
                } else {
                    throw new RuntimeException("팔로우안되어 있음");
                }
            } else {
                throw new RuntimeException("사용자 찾을 수 없음");
            }
        } else {
            throw new RuntimeException("현재 사용자 찾을 수 없음");
        }
    }

    @Transactional(readOnly = true)
    public List<RecommendedUserDto> getRecommendedUsers(String currentUserEmail) {

        Optional<User> optionalCurrentUser = userRepository.findByUserEmail(currentUserEmail);

        if (!optionalCurrentUser.isPresent()) {
            throw new IllegalArgumentException("현재 사용자 찾을 수 없음 " + currentUserEmail);
        }
        User currentUser = optionalCurrentUser.get();

        List<User> followedUsers = currentUser.getFollowingList().stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toList());

        followedUsers.add(currentUser);

        List<User> recommendedUsers = userRepository.findAll()
                .stream()
                .filter(user -> !followedUsers.contains(user))
                .collect(Collectors.toList());

        Collections.shuffle(recommendedUsers);

        return recommendedUsers.stream()
                .limit(6)
                .map(user -> RecommendedUserDto.fromEntity(user, currentUserEmail)) // 여기를 수정했습니다.
                .collect(Collectors.toList());
    }
}
