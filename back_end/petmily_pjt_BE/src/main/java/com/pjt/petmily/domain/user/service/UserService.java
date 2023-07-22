package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.Role;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@Service
//@Transactional
//@RequiredArgsConstructor
public interface UserService {

//    private final UserRepository userRepository;
////    private final PasswordEncoder passwordEncoder;
//
//    public void signUp(UserSignUpDto userSignUpDto) throws Exception {
//
//        //중복체크
//        if (userRepository.findByUserEmail(userSignUpDto.getUserEmail()).isPresent()){
//            throw new Exception("이미 존재하는 이메일입니다.");
//        }
//
//        //저장
//        User user = User.builder()
//                .userEmail(userSignUpDto.getUserEmail())
//                .userPw(userSignUpDto.getUserPw())
//                .role(Role.USER)
//                .build();
//
////        user.passwordEncode(passwordEncoder);
//        userRepository.save(user);
//    }
    boolean checkEmailExists(String userEmail);

    //회원가입


//    UserSignUpDto signUp(UserSignUpDto userSignUpDto);
    boolean loginUser(String userEmail, String password);

    boolean loginUser(UserLoginDto userLoginDto);


    
}
