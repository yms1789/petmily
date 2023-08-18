package com.pjt.petmily.domain.noti.repository;

import java.util.List;

import com.pjt.petmily.domain.noti.entity.Noti;
import com.pjt.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotiRepository extends JpaRepository<Noti, Integer>{

    List<Noti> findByToUserAndIsCheckedFalseOrderByIdDesc(User user);

    List<Noti> findByToUserOrderByIdDesc(User user);

    boolean existsByToUserAndIsCheckedFalse(User user);
}
