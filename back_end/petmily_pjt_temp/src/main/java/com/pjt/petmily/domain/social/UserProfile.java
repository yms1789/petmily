package com.pjt.petmily.domain.social;


import com.pjt.petmily.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

    private String userEmail;
    private String provider;

    public User toUser(){
        return User.builder()
                .userEmail(userEmail)
                .build();
    }
}
