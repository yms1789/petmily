package com.pjt.petmily.domain.user.follow.dto;

import com.pjt.petmily.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedUserDto {
    private String userEmail;
    private String userNickname;
    private String userLikePet;
    private String userProfileImg;
    private boolean followedByCurrentUser;

    public static RecommendedUserDto fromEntity(User user, String currentUserEmail) {
        RecommendedUserDto dto = new RecommendedUserDto();
        dto.setUserEmail(user.getUserEmail());
        dto.setUserNickname(user.getUserNickname());
        dto.setUserLikePet(user.getUserLikePet());
        dto.setUserProfileImg(user.getUserProfileImg());

        // Check if this user is followed by the current user
        boolean followedByCurrentUser = user.getFollowerList().stream()
                .anyMatch(follow -> follow.getFollower().getUserEmail().equals(currentUserEmail));

        dto.setFollowedByCurrentUser(followedByCurrentUser);

        return dto;
    }
}