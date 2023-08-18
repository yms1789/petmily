package com.pjt.petmily.domain.user.follow.dto;

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
    private boolean followingCurrentUser;

    public static FollowingDto fromFollowEntity(Follow follow, String currentUser) {
        FollowingDto followingDto = new FollowingDto();
        followingDto.setUserId(follow.getFollowing().getUserId());
        followingDto.setUserEmail(follow.getFollowing().getUserEmail());
        followingDto.setUserNickname(follow.getFollowing().getUserNickname());
        followingDto.setUserProfileImg(follow.getFollowing().getUserProfileImg());

        followingDto.setFollowingCurrentUser(follow.getFollower().getUserEmail().equals(currentUser));


        return followingDto;
    }
}
