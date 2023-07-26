package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;



public interface UserService {


    //회원가입
    User signUp(UserSignUpDto userSignUpDto);


    ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto);


    
}
