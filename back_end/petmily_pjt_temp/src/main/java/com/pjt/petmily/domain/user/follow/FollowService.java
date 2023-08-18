package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import com.pjt.petmily.domain.user.follow.dto.RecommendedUserDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface FollowService {
    String followUser(String userEmail, FollowUserDto followUserDto);

    String unfollowUser(String userEmail, FollowUserDto followUserDto);

    List<RecommendedUserDto> getRecommendedUsers(String currentUserEmail);
}

