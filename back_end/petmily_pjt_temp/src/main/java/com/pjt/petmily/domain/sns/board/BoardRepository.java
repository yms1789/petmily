package com.pjt.petmily.domain.sns.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    List<Board> findByHashTagList_HashTagName(String hashTagName);

    @Modifying
    @Query("update Board b set b.heartCount = b.heartCount + 1 where b = ?1")
    void addHeartCount(Board board);

    @Modifying
    @Query("update Board b set b.heartCount = b.heartCount - 1 where b = ?1")
    void subHeartCount(Board board);

    @Query(value = "select p from Board p where p.boardId < ?1 order by p.boardId desc ")
    Page<Board> findByPostIdLessThanOrderByPostIdDesc(Long lastPostId, PageRequest pageRequest);
}
