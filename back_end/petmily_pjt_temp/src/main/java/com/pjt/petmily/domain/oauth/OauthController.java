package com.pjt.petmily.domain.oauth;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class OauthController {

    @Autowired
    private OAuthService oAuthService;

    @ApiOperation(value="카카오 로그인", notes="카카오 로그인")
    @ResponseBody
    @GetMapping("/login/kakao")
    public void kakaoCallback(@ApiParam(value = "kakao auth code", required = true) @RequestParam String code){
        String accessToken = oAuthService.getKakaoAccessToken(code);

        HashMap<String, Object> userInfo = oAuthService.getUserInfo(accessToken);

    }
}
