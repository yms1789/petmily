package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.user.dto.UserEmailVerifyDto;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpEmailDto;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pjt.petmily.global.jwt.service.JwtService;
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
    public ResponseEntity<String> emailConfirm(@ModelAttribute UserSignUpEmailDto userSignUpEmailDto) throws Exception {
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


    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(UserLoginDto dto) throws Exception{
        String userEmail = dto.getEmail();
        String userPw = dto.getPassword();
        Optional<User> user = userService.findOne(userEmail);
//        boolean loginUser = userService.loginUser(dto.getEmail(),dto.getPassword());
        if (user.isPresent() && user.get().getUserPw().equals(userPw)) {
            // 로그인 성공 시, AccessToken과 RefreshToken 생성
            String accessToken = jwtService.createAccessToken(userEmail);
            String refreshToken = jwtService.createRefreshToken(userEmail);

            // AccessToken과 RefreshToken을 헤더에 실어서 응답
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("RefreshToken", refreshToken);
            user.get().updateUserToken(refreshToken);
            userRepository.save(user.get());
            return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }


    // 로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "로그아웃";
    }

    // 비밀번호초기화
    @PostMapping("/resetpassword/email")
    public String emailCheck(@RequestParam String email) throws Exception {
        boolean emailExists = userService.checkEmailExists(email);
        // 이메일 중복 확인
        if (emailExists) {
            String confirm = emailService.sendSimpleMessage(email);
            return confirm;
        } else {
            return "존재하지 않는 이메일";
        }
    }

}
