package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    public String followUser(Long userId, FollowUserDto followUserDto) {
        Optional<User> currentUser = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if(((Optional<?>) currentUser).isPresent()) {
            User user = currentUser.get();
            Optional<User> targetUserOptional = userRepository.findById(userId);
            if(targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                Follow follow = Follow.builder()
                        .follower(user)
                        .following(targetUser)
                        .build();
                followRepository.save(follow);
                return "팔로우 성공";
            } else {
                throw new RuntimeException("사용자 찾을 수 없음");
            }
        } else {
            throw new RuntimeException("현재 사용자 찾을 수 없음");
        }
    }

    public String unfollowUser(Long userId, FollowUserDto followUserDto) {
        Optional<User> currentUser = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if(currentUser.isPresent()) {
            User user = currentUser.get();
            Optional<User> targetUserOptional = userRepository.findById(userId);
            if(targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                Optional<Follow> followOptional = followRepository.findByFollowerAndFollowing(user, targetUser);
                if(followOptional.isPresent()) {
                    followRepository.delete(followOptional.get());
                    return "Unfollowed Successfully";
                } else {
                    throw new RuntimeException("Follow not found");
                }
            } else {
                throw new RuntimeException("Target User not found");
            }
        } else {
            throw new RuntimeException("Current User not found");
        }
    }
}
