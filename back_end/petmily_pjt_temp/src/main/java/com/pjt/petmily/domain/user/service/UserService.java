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

    // 비밀번호 변경
    ResponseDto<String> changePassword(String userEmail, String newPw);

    // 이메일, 패스워드 일치여부 반환(1,0)
    boolean passwordCheck(String userEmail, String old_password);

    // 회원탈퇴(DB회원정보삭제)
    ResponseDto<String> deleteUser(String userEmail);
    
}
