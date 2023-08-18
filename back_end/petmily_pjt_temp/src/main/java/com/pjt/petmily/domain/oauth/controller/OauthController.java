package com.pjt.petmily.domain.oauth.controller;

import com.pjt.petmily.domain.oauth.service.OAuthService;
import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    @Operation(summary="카카오 로그인", description="카카오 로그인")
    @ResponseBody
    @GetMapping("/oauth/kakao")
    public ResponseDto<LoginResponseDto> kakaoCallback(@Parameter(description = "kakao auth code", required = true) @RequestParam String code){
        String accessToken = oAuthService.getKakaoAccessToken(code);
        ResponseDto<LoginResponseDto> result = oAuthService.getUserInfo(accessToken);
        return result;
    }
}
