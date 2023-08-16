package com.pjt.petmily.global.FCM;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
    private String userEmail;
    private String fcmToken;
}