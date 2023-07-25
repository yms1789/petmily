package com.pjt.petmily.domain.social;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {

    private final SocialLoginService socialLoginService;

    @PostMapping("/login/google")
    public ResponseEntity<Map<String, Object>> getGoogleAccessToken(@RequestHeader(value = "Authorization") String code){
        String accessToken = null;

        if (code != null && code.startsWith("Bearer ")) {
            accessToken = code.substring(7);
        }

        if (accessToken == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            Map<String, Object> userInfo = socialLoginService.getGoogleUserInfo(accessToken);
            return new ResponseEntity<>(userInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
