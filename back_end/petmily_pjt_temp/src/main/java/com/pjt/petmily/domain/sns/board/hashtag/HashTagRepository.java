package com.pjt.petmily.domain.sns.board.hashtag;

import com.pjt.petmily.domain.sns.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    List<HashTag> findByBoard(Board board);

}
