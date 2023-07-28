package com.pjt.petmily.domain.oauth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    // 프론트에서 넘겨주는 url에서 code를 받아옴
    @Operation(summary="카카오 로그인", description="카카오 로그인")
    @ResponseBody
    @PostMapping("/login/kakao")
    public void kakaoCallback(@Parameter(description = "kakao auth code", required = true) @RequestParam String code){

        // 코드를 이용해서 카카오서버로부터 accessToken 발급
        String accessToken = oAuthService.getKakaoAccessToken(code);

        // accessToken으로 유저정보를 받아옴
        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);

    }
}
