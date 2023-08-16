package com.pjt.petmily.domain.user.repository;

import com.pjt.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserNickname(String userNickname);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByUserToken(String userToken);

}