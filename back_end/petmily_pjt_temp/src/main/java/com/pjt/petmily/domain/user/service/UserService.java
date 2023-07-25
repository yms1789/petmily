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

import java.util.Optional;

//@Service
//@Transactional
//@RequiredArgsConstructor
public interface UserService {

    // 이메일 중복 확인
    boolean checkEmailExists(String userEmail);

    //회원가입
    User signUp(UserSignUpDto userSignUpDto);


    boolean loginUser(String userEmail, String password);

    Optional<User> findOne(String userEmail);
//    boolean loginUser(UserLoginDto userLoginDto);

    
}
