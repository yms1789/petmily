package com.pjt.petmily.domain.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private UserLoginInfoDto userLoginInfoDto;
}
