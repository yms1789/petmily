package com.pjt.petmily.domain.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Data
public class UserSignUpEmailDto {
    private String userEmail;
}
