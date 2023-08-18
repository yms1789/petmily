package com.pjt.petmily.domain.sns.heart.repository;

import com.pjt.petmily.domain.sns.board.entity.Board;
import com.pjt.petmily.domain.sns.heart.entity.Heart;
import com.pjt.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByUserAndBoard(User user, Board board);

}
