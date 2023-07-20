package com.example.petmily_BE.domain.user.service;

import com.example.petmily_BE.domain.user.User;
import com.example.petmily_BE.domain.user.dto.UserLoginDto;
import com.example.petmily_BE.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByEmailAndPassword(userLoginDto.getEmail(),userLoginDto.getPassword());
        if (user != null && user.getPassword().equals(userLoginDto.getPassword())) {
            return true; // 로그인 성공
        } else {
            return false; // 로그인 실패
        }
    }
}