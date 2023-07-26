package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.password.PasswordEncoder;


@Service
//@Transactional
@RequiredArgsConstructor
public interface UserService {

    // 이메일 중복 확인
    boolean checkEmailExists(String userEmail);

    // 이메일 코드 확인
//    boolean verifyCode(String userEmail, String code);

    //회원가입
    User signUp(UserSignUpDto userSignUpDto);


    ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto);


    
}
