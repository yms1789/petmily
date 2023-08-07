package com.pjt.petmily.domain.user.follow.dto;

import com.pjt.petmily.domain.user.follow.Follow;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FollowerDto {
    private String userEmail;
    private String userNickname;
    private String userProfileImg;
    private boolean followedByCurrentUser;

    public static FollowerDto fromFollowEntity(Follow follow, String currentUserEmail){
        FollowerDto followerDto = new FollowerDto();
        followerDto.setUserEmail(follow.getFollower().getUserEmail());
        followerDto.setUserNickname(follow.getFollower().getUserNickname());
        followerDto.setUserProfileImg(follow.getFollower().getUserProfileImg());

        followerDto.setFollowedByCurrentUser(follow.getFollowing().getUserEmail().equals(currentUserEmail));

        return followerDto;
    }
}

