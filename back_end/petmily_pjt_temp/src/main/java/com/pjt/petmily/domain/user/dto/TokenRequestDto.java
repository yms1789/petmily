package com.pjt.petmily.domain.user.dto;


import lombok.Data;

@Data
public class TokenRequestDto {
    private String userEmail;
    private String refreshToken;
}
