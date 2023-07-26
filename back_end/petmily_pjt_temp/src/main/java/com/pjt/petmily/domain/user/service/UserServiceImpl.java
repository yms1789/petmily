package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 중복 이메일 확인
    @Override
    public boolean checkEmailExists(String userEmail) {
        return userRepository.findByUserEmail(userEmail).isPresent();
    }

    // 이메일 코드 확인
//    @Override
//    public boolean verifyCode(String userEmail, String code) {
//        String correctCode =
//    }

    // 회원가입완료 (DB 저장)
    @Override
    public User signUp(UserSignUpDto userSignUpDto) {

        User user = User.builder()
                .userEmail(userSignUpDto.getUserEmail())
                .userPw(bCryptPasswordEncoder.encode(userSignUpDto.getUserPw()))
                .build();
        userRepository.save(user);

        return user;
    }

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }




//    @Override
//    public User loginUser(UserLoginDto userLoginDto) {
//        String userEmail = userLoginDto.getUserEmail();
//        String userPw = userLoginDto.getUserPw();
//        Optional<User> existEmail = userRepository.findByUserEmail(userEmail);
//        if (existEmail.isPresent()) {
//            User user = existEmail.get();
//            if (bCryptPasswordEncoder.matches(userPw, existEmail.get().getUserPw())) {
//                String refreshToken = JwtService.createRefreshToken(userEmail);
////                existEmail.get().updateUserToken(refreshToken);
//                user.updateUserToken(refreshToken);
//                userRepository.save(user);
//                return user;
//
//            }
//        }
//        return null;
//    }

    @Override
    public ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto) {
        String userEmail = userLoginDto.getUserEmail();
        String userPw = userLoginDto.getUserPw();
        Optional<User> existEmail = userRepository.findByUserEmail(userEmail);
        if (existEmail.isPresent()) {
            User user = existEmail.get();
            if (bCryptPasswordEncoder.matches(userPw, existEmail.get().getUserPw())) {
                String refreshToken = JwtService.createRefreshToken(userEmail);
                String accessToken = JwtService.createAccessToken(userEmail);
                user.updateUserToken(refreshToken);
                userRepository.save(user);

                LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, user);
                return ResponseDto.setSucess("로그인성공", loginResponseDto);

            }
        }
        return ResponseDto.setFailed("이메일이 존재하지 않거나 비밀번호가 틀림");

    }

    // 이메일 입력받으면 비밀번호 변경
    @Override
    public ResponseDto<String> changePassword(String userEmail, String newPw) {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        User user = findUser.get();
        user.changeUserPw(newPw);
        userRepository.save(user);
        return ResponseDto.setSucess("비밀번호 변경 선공",null);
    }
}
