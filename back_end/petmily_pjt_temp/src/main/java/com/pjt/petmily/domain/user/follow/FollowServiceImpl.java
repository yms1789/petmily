package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/*
팔로워 : 나를 팔로우한 사람
팔로잉 : 내가 팔로우한 사람
 */

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    @Transactional
    public String followUser(String userEmail, FollowUserDto followUserDto) {
        Optional<User> currentUserOptional = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if (currentUserOptional.isPresent()) {
            User user = currentUserOptional.get();
            Optional<User> targetUserOptional = userRepository.findByUserEmail(userEmail);
            if (targetUserOptional.isPresent()) {
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
