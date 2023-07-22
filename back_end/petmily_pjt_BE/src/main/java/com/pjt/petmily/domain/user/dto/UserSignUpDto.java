package com.pjt.petmily.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String userEmail;
    private String userPw;

    private String userNickname;
    private String userRegion;
    private String userLikePet;

    private String userProfileImg;
}
