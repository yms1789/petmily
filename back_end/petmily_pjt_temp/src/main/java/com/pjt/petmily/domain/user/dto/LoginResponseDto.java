package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.user.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private String accessToken;
    private User user;
}
