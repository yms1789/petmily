package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpEmailDto;
import com.pjt.petmily.domain.user.service.EmailService;
import com.pjt.petmily.domain.user.service.EmailServiceImpl;
import com.pjt.petmily.domain.user.service.UserService;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 이메일 인증 번호 전송
    @PostMapping("/signup/email")
    @ApiOperation(value = "이메일 확인", notes = "회원 가입 시 이메일 중복 확인 및 이메일 인증 코드 발송")
    @ApiResponses({
            @ApiResponse(code = 200, message = "이메일 인증 코드 발송 성공 또는 이미 존재하는 이메일 메시지 반환"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> emailConfirm(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        boolean emailExists = userService.checkEmailExists(userSignUpEmailDto.getUserEmail());

        // 이메일 중복 확인
        if (emailExists) {
            return new ResponseEntity<>("이미 존재하는 이메일입니다", HttpStatus.UNAUTHORIZED);
        } else {
            String confirm = emailService.sendSimpleMessage(userSignUpEmailDto.getUserEmail());

            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }
    }

    // 이메일 인증 코드 확인
    @PostMapping("/signup/email/verification")
    @ApiOperation(value = "이메일 인증 코드 확인", notes = "회원 가입 시 이메일 인증 코드 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증 코드 일치"),
            @ApiResponse(code = 401, message = "인증 코드 불일치"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> verifyCode(@RequestParam("code") String code, @RequestParam("userEmail") String userEmail) {
        String ePw = emailService.getVerificationCode(userEmail);
        System.out.println("code : " + code);
        System.out.println("code match : " + ePw.equals(code));

        if (ePw.equals(code)){
            return new ResponseEntity<>(HttpStatus.OK); // 인증 코드 일치
        } else {
            return new ResponseEntity<>("인증 코드가 일치하지 않습니다", HttpStatus.UNAUTHORIZED); // 인증 코드 불일치
        }
    }

    // 회원가입
    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입", notes = "회원 가입을 위한 메소드")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "잘못된 요청"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }

//    @PostMapping("/login")
//    public String login(UserLoginDto dto) {
//        boolean loginUser = userService.loginUser(dto);
//        if (loginUser)
//            return "로그인";
//        return "로그인실패";
//    }

    @PostMapping("/logout")
    public String logout() {
        return "로그아웃";
    }
    // 로그인


}
