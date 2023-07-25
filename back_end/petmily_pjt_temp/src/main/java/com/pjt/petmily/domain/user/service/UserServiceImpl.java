package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
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


    public Optional<User> findOne(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }


    @Override
    public boolean loginUser(String userEmail, String password) {
        Optional<User> user = findOne(userEmail);
        if (user.isPresent()) {
            return user.get().getUserPw().equals(password);
        }
        return false;
    }
}
