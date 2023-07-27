package com.pjt.petmily.domain.user.service;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;


public interface UserService {


    //회원가입
    User signUp(UserSignUpDto userSignUpDto);


    ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto);

    boolean checkNicknameExists(String userNickname);

    // 회원 정보 수정
    User infoEdit(UserInfoEditDto userInfoEditDto);

    
}
