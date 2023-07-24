package com.pjt.petmily.domain.user.service;


import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceLoginImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceLoginImpl(UserRepository repository) {
        this.userRepository =  repository;
    }

    @Override
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

//    public void addUser(String userEmail, String userPw) {
//        User user = new User();
//        user.getUserEmail()
//        // 나머지 필드 값 설정 (필요에 따라 추가)
//
//        userRepository.save(user);
//    }
//    @Override
//    public boolean loginUser(UserLoginDto userLoginDto) {
//        return false;
//    }
}
