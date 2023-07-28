package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 중복 이메일 확인


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

    // 닉네임 중복 확인
    @Override
    public boolean checkNicknameExists(String userNickname) {
        return userRepository.findByUserNickname(userNickname).isPresent();
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

    @Override
    @Transactional
    public void updateUserInfo(UserInfoEditDto userInfoEditDto) {
        User user = userRepository.findByUserEmail(userInfoEditDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String userNickname = userInfoEditDto.getUserNickname();
        String userLikePet = userInfoEditDto.getUserLikePet();

        System.out.println(userNickname +" " + userLikePet);
        user.updateUserInfo(userNickname, userLikePet);
    }

    @Override
    @Transactional
    public void updateUserImg(String userEmail, String userProfileImg){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        user.updateUserImg(userProfileImg);
    }
}