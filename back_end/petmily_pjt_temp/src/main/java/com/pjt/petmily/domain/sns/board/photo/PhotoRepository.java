package com.pjt.petmily.domain.sns.board.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findByBoard_BoardId(Long boardId);
}
