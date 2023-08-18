package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String userEmail;
    private String userPw;
}
