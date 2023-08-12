package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
//import org.springframework.security.crypto.password.PasswordEncoder;



public interface UserService {


    //회원가입
    User signUp(UserSignUpDto userSignUpDto);

    ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto);

    boolean checkNicknameExists(String userNickname);

    // 회원 정보 수정
    String updateUserInfo(UserInfoEditDto userInfoEditDto);
    Optional<String> updateUserImg(String userEmail, MultipartFile file) throws Exception;
    // 비밀번호 변경
    ResponseDto<String> changePassword(String userEmail, String newPw);

    // 이메일, 패스워드 일치여부 반환(1,0)
    boolean passwordCheck(String userEmail, String old_password);

    // 회원탈퇴(DB회원정보삭제)
    ResponseDto<String> deleteUser(String userEmail);


    // 출석체크
    boolean attendance(UserSignUpEmailDto userEmailDto);
    
}
