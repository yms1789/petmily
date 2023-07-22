package com.pjt.petmily.domain.user.service;


import com.pjt.petmily.domain.user.Role;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.dto.UserSignUpDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceLoginImpl implements UserService {
    private UserRepository userRepository;

    // 중복 이메일 확인
    @Override
    public boolean checkEmailExists(String userEmail) {
        return userRepository.findByUserEmail(userEmail).isPresent();
    }

    // 회원가입완료 (DB 저장)
    @Override
    public UserSignUpDto signUp(UserSignUpDto userSignUpDto){
        User user = userRepository.save(User.builder()
                .userEmail(userSignUpDto.getUserEmail())
                .role(Role.USER)
                .build());
        return UserSignUpDto.builder()
                .userId(user.getUserId())
                .userEmail(user.getUserEmail());
    }

    @Autowired
    public UserServiceLoginImpl(UserRepository repository) {
        this.userRepository =  repository;
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

    @Override
    public boolean loginUser(UserLoginDto userLoginDto) {
        return false;
    }
}
