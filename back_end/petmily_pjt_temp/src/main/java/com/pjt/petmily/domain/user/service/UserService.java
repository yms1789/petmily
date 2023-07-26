package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;
//import org.springframework.security.crypto.password.PasswordEncoder;


//@Service
//@Transactional
//@RequiredArgsConstructor
public interface UserService {

    // 이메일 중복 확인
    boolean checkEmailExists(String userEmail);

    // 이메일 코드 확인
//    boolean verifyCode(String userEmail, String code);

    //회원가입
    User signUp(UserSignUpDto userSignUpDto);


    ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto);

    ResponseDto<String> changePassword(String userEmail, String newPw);
    
}
