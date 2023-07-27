package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String userEmail;
    private String userPw;


    /* DTO -> Entity */
    public User toEntity() {
        User user = User.builder()
                .userEmail(userEmail)
                .userPw(userPw)
                .build();
        return user;
    }
}
