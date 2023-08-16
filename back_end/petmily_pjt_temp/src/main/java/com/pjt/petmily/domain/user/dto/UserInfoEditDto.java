package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoEditDto {

    private String userEmail;
//    private String userProfileImg;
    private String userNickname;
    private String userLikePet;

//    public void setUserProfileImg(String userProfileImg){
//        this.userProfileImg = this.userProfileImg;
//    }

    /*
    Entity -> dto
     */
    public UserInfoEditDto(User user){
        this.userEmail = user.getUserEmail();
//        this.userProfileImg = user.getUserProfileImg();
        this.userNickname = user.getUserNickname();
        this.userLikePet = user.getUserLikePet();
    }

}
