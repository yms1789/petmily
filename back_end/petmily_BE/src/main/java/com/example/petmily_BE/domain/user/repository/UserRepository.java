package com.example.petmily_BE.domain.user.repository;

import com.example.petmily_BE.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
    // 추가적인 쿼리 메소드 등을 선언할 수 있습니다.
}
