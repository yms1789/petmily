package com.pjt.petmily.domain.user.repository;


import com.pjt.petmily.domain.user.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findByUser_UserId(Long userId);
}
