package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.service.EmailService;
import com.pjt.petmily.domain.user.service.EmailServiceImpl;
import com.pjt.petmily.domain.user.service.UserService;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }
    // 회원가입
//    @PostMapping("/signup")
//    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
//        userService.signUp(userSignUpDto);
//        return "회원가입 성공";
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(UserLoginDto dto) {
        String userEmail = dto.getEmail();
        String userPw = dto.getPassword();
        Optional<User> user = userService.findOne(userEmail);
//        boolean loginUser = userService.loginUser(dto.getEmail(),dto.getPassword());
        if (user.isPresent() && user.get().getUserPw().equals(userPw)) {
            // 로그인 성공 시, AccessToken과 RefreshToken 생성
            String accessToken = jwtService.createAccessToken(userEmail);
            String refreshToken = jwtService.createRefreshToken(userEmail);

            // AccessToken과 RefreshToken을 헤더에 실어서 응답
            System.out.println("--------------------------------------------------------");
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

    @PostMapping("/logout")
    public String logout() {
        return "로그아웃";
    }
    // 로그인


}
