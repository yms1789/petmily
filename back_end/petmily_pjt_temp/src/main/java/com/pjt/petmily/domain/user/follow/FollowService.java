package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import org.springframework.stereotype.Service;


@Service
public interface FollowService {
    String followUser(String userEmail, FollowUserDto followUserDto);

    String unfollowUser(String userEmail, FollowUserDto followUserDto);
}

