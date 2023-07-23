package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.user.Role;
import com.pjt.petmily.domain.user.User;
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

    /* DTO -> Entity */
    public User toEntity() {
        User user = User.builder()
                .userEmail(userEmail)
                .userPw(userPw)
                .userNickname(userNickname)
                .userRegion(userRegion)
                .userLikePet(userLikePet)
                .role(Role.USER)
                .build();
        return user;
    }
}
