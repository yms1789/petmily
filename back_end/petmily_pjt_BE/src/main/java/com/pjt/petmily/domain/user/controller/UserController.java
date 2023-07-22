package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.service.EmailService;
import com.pjt.petmily.domain.user.service.EmailServiceImpl;
import com.pjt.petmily.domain.user.service.UserService;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 이메일 인증 번호 전송
    @PostMapping("/signup/email")
    public String emailConfirm(@RequestParam String email) throws Exception {

        boolean emailExists = userService.checkEmailExists(email);

        // 이메일 중복 확인
        if (emailExists) {
            return "이미 존재하는 이메일입니다";
        } else {
            String confirm = emailService.sendSimpleMessage(email);

            return confirm;
        }

    }
    // 회원가입
//    @PostMapping("/signup")
//    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
//        userService.signUp(userSignUpDto);
//        return "회원가입 성공";
//    }

    @PostMapping("/login")
    public String login(UserLoginDto dto) {
        boolean loginUser = userService.loginUser(dto);
        if (loginUser)
            return "로그인";
        return "로그인실패";
    }

    @PostMapping("/logout")
    public String logout() {
        return "로그아웃";
    }
    // 로그인


}
