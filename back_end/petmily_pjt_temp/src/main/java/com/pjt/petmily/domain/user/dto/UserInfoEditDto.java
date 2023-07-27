package com.pjt.petmily.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoEditDto {

    private String userEmail;
    private String userProfileImg;
    private String userNickname;
    private String userLikePet;

    public void setUserProfileImg(String userProfileImg){
        this.userProfileImg = this.userProfileImg;
    }

}
