package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.service.EmailService;
import com.pjt.petmily.domain.user.service.EmailServiceImpl;
import com.pjt.petmily.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pjt.petmily.global.jwt.service.JwtService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

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

    // 이메일 인증 코드 확인(회원가입, 비밀번호초기화)
    @PostMapping("/email/verification")
    @ApiOperation(value = "이메일 인증 코드 확인", notes = "회원 가입 시 이메일 인증 코드 확인")
    @ApiResponses({
            @ApiResponse(code = 200, message = "인증 코드 일치"),
            @ApiResponse(code = 401, message = "인증 코드 불일치"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<String> verifyCode(@RequestBody UserEmailVerifyDto userEmailVerifyDto) {
        String ePw = emailService.getVerificationCode(userEmailVerifyDto.getUserEmail());
        System.out.println("code : " + userEmailVerifyDto.getCode());
        System.out.println("code match : " + ePw.equals(userEmailVerifyDto.getCode()));

        if (ePw.equals(userEmailVerifyDto.getCode())){
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


    // 로그인(accessToken만 줌)
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
//        User authenticatedUser = userService.loginUser(userLoginDto);
//        if (authenticatedUser != null) {
//            String accessToken = JwtService.createAccessToken(authenticatedUser.getUserEmail());
//            return ResponseEntity.ok(accessToken);
//        } else {
//            return new ResponseEntity<>("로그인실패", HttpStatus.UNAUTHORIZED);
//        }
//    }

    // 로그인(정보 다줌)
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        ResponseDto<LoginResponseDto> result = userService.loginUser(userLoginDto);
        return result;
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "로그아웃";
    }

    // 비밀번호 초기화 - 인증코드 발송
    @PostMapping("/resetpassword/email")
    public ResponseEntity<String> emailCheck(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        boolean emailExists = userService.checkEmailExists(userSignUpEmailDto.getUserEmail());
        // 이메일 유무확인
        if (emailExists) {
            String confirm = emailService.sendSimpleMessage(userSignUpEmailDto.getUserEmail());
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("존재하지 않는 이메일입니다", HttpStatus.UNAUTHORIZED);
        }
    }

    // 비밀번호 초기화 - 초기화된 비밀번호 이메일로 발송
    @PutMapping("/resetpassword/reset")
    public ResponseDto<String> passwordReset(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        String sendNewPw = emailService.sendNewPasswordMessage(userSignUpEmailDto.getUserEmail());
        ResponseDto<String> result = userService.changePassword(userSignUpEmailDto.getUserEmail(), sendNewPw);
        return result;
    }

    // 비밀번호 변경
    @PutMapping("/changepassword")
    public String changePassword(@RequestParam String userEmail,
                                 @RequestParam String old_password,
                                 @RequestParam String new_password) throws Exception {
        boolean passwordCheck = userService.passwordCheck(userEmail, old_password);
        if (passwordCheck) {
            userService.changePassword(userEmail, new_password);
            return "비밀번호가 변경 되었습니다.";
        } else {
            return "기존 비밀번호가 일치하지 않습니다.";
        }
    }
}
