package com.pjt.petmily.domain.user.follow;

import org.springframework.stereotype.Service;

@Service
public interface FollowService {
    String followUser(Long userId, FollowUserDto followUserDto);

    String unfollowUser(Long userId, FollowUserDto followUserDto);
}

