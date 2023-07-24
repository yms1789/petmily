package com.pjt.petmily.domain.user.repository;

import com.pjt.petmily.domain.user.Role;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @AfterEach
    public void after(){
        em.clear();
    }

    // 회원저장 성공
    @Test
    public void 회원저장_성공() throws Exception{
        //given
        User user = User.builder().userEmail("userEmail").userPw("1234").role(Role.USER).build();

        //when
        User saveUser = userRepository.save(user);
        //then
        User findUser = userRepository.findById(saveUser.getUserId()).orElseThrow(()-> new RuntimeException("저장된 회원이 없습니다."));

        assertThat(findUser).isSameAs(saveUser);
        assertThat(findUser).isSameAs(user);
    }

}