package com.pjt.petmily.domain.sns.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByBoardId(Long boardId);

    List<Board> findAll();

    @Modifying
    @Query("update Board b set b.heartCount = b.heartCount + 1 where b = ?1")
    void addHeartCount(Board board);

    @Modifying
    @Query("update Board b set b.heartCount = b.heartCount - 1 where b = ?1")
    void subHeartCount(Board board);
}
