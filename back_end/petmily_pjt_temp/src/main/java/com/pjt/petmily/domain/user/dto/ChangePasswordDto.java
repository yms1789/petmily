package com.pjt.petmily.domain.user.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String userEmail;
    private String oldPassword;
    private String newPassword;


}
