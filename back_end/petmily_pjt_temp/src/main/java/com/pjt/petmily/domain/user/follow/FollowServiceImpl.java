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
    public String followUser(Long userId, FollowUserDto followUserDto) {
        Optional<User> currentUserOptional = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if(currentUserOptional.isPresent()) {
            User user = currentUserOptional.get();  //팔로우하는 사람 (상대 유저 팔로워에 저장)
            Optional<User> targetUserOptional = userRepository.findByUserId(userId);  //팔로우될사람 (팔로잉에 user를 저장)
            if(targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                Follow follow = Follow.builder()
                        .follower(user)
                        .following(targetUser)
                        .build();

                user.addFollowing(follow);
                targetUser.addFollower(follow);

                followRepository.save(follow);
                userRepository.save(user);
                userRepository.save(targetUser);
                return "팔로우 성공";
            } else {
                throw new RuntimeException("사용자 찾을 수 없음");
            }
        } else {
            throw new RuntimeException("현재 사용자 찾을 수 없음");
        }
    }


    @Transactional
    public String unfollowUser(Long userId, FollowUserDto followUserDto) {
        Optional<User> currentUserOptional = userRepository.findByUserEmail(followUserDto.getUserEmail());
        if(currentUserOptional.isPresent()) {
            User user = currentUserOptional.get();
            Optional<User> targetUserOptional = userRepository.findByUserId(userId);
            if(targetUserOptional.isPresent()) {
                User targetUser = targetUserOptional.get();
                Optional<Follow> followOptional = followRepository.findByFollowerAndFollowing(user, targetUser);
                if(followOptional.isPresent()) {
                    Follow follow = followOptional.get();

                    user.removeFollowing(follow);
                    targetUser.removeFollower(follow);

                    followRepository.delete(follow);

                    userRepository.save(user);
                    userRepository.save(targetUser);

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
