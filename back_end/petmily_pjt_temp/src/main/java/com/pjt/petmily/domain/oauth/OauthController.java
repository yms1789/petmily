package com.pjt.petmily.domain.oauth;

import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@Controller
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    // 프론트에서 넘겨주는 url에서 code를 받아옴
    @Operation(summary="카카오 로그인", description="카카오 로그인")
    @ResponseBody
    @GetMapping("/kakao")
    public ResponseDto<LoginResponseDto> kakaoCallback(@Parameter(description = "kakao auth code", required = true) @RequestParam String code){

        // 코드를 이용해서 카카오서버로부터 accessToken 발급
        String accessToken = oAuthService.getKakaoAccessToken(code);

        // accessToken으로 유저정보를 받아옴
//        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);

        ResponseDto<LoginResponseDto> result = oAuthService.getUserInfo(accessToken);

        return result;
    }
}
