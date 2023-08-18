package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.shop.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginInfoDto {
    String userEmail;
    String userToken;
    String userNickname;
    String userProfileImg;
    String userLikePet;
    Item userBadge;
    Item userRing;
    Item userBackground;
    Long userLoginDate;
    Boolean userIsSocial;
    LocalDateTime userAttendance;
}
