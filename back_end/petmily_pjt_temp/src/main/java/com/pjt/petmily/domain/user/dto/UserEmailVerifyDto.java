package com.pjt.petmily.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserEmailVerifyDto {
    private String userEmail;
    private String code;
}
