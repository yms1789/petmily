package com.pjt.petmily.domain.user.repository;

import com.pjt.petmily.domain.user.SocialType;
import com.pjt.petmily.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);

    Optional<User> findByUserToken(String userToken);

    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}