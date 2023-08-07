package com.pjt.petmily.domain.user.follow.dto;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.follow.Follow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowingDto {
    private Long userId;
    private String userEmail;
    private String userNickname;
    private String userProfileImg;
    private boolean followedByCurrentUser;

    public static FollowingDto fromFollowEntity(Follow follow, String currentUserEmail) {
        FollowingDto followingUserDto = new FollowingDto();
        followingUserDto.setUserId(follow.getFollowing().getUserId());
        followingUserDto.setUserEmail(follow.getFollowing().getUserEmail());
        followingUserDto.setUserNickname(follow.getFollowing().getUserNickname());
        followingUserDto.setUserProfileImg(follow.getFollowing().getUserProfileImg());
        followingUserDto.setFollowedByCurrentUser(follow.getFollower().getUserEmail().equals(currentUserEmail));

        return followingUserDto;
    }
}
