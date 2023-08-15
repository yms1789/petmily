package com.pjt.petmily.domain.user.follow.dto;

import com.pjt.petmily.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendedUserDto {
    private String userEmail;
    private String userNickname;
    private String userProfileImg;

    public static RecommendedUserDto fromEntity(User user) {
        RecommendedUserDto dto = new RecommendedUserDto();
        dto.setUserEmail(user.getUserEmail());
        dto.setUserNickname(user.getUserNickname());
        dto.setUserProfileImg(user.getUserProfileImg());
        return dto;
    }
}