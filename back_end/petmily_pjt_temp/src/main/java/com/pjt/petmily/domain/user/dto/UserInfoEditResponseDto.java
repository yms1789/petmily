package com.pjt.petmily.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoEditResponseDto {
    private UserInfoEditDto userInfo;
    private String imageUrl;

    public UserInfoEditResponseDto(UserInfoEditDto userInfoEditDto, String userProfilImg) {
        this.userInfo = userInfoEditDto;
        this.imageUrl = userProfilImg;
    }
}
