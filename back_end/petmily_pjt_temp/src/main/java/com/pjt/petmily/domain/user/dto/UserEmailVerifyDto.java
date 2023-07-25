package com.pjt.petmily.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserEmailVerifyDto {
    private String userEmail;
    private String code;
}
