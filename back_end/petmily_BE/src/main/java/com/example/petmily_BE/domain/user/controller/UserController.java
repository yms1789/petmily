package com.example.petmily_BE.domain.user.controller;

import com.example.petmily_BE.domain.user.jwt.JwtTokenProvider;
import com.example.petmily_BE.domain.user.dto.UserLoginDto;
import com.example.petmily_BE.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        // 로그인 로직 수행
        if (userService.loginUser(userLoginDto)) {
            // 로그인 성공 시 JWT 토큰 생성하여 반환
            String token = jwtTokenProvider.createToken(userLoginDto.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

}
