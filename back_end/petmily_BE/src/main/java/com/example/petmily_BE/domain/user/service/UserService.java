package com.example.petmily_BE.domain.user.service;

import com.example.petmily_BE.domain.user.dto.UserLoginDto;
import com.example.petmily_BE.domain.user.dto.UserSignUpDto;

public interface UserService {

    // 회원 가입 기능
//    void signUpUser(UserSignUpDto userSignUpDto);

    // 로그인 기능
    boolean loginUser(UserLoginDto userLoginDto);
}