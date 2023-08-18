package com.pjt.petmily.domain.sns.comment.repository;

import com.pjt.petmily.domain.sns.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface CommentRepository  extends JpaRepository<Comment, Long> {

    Optional<Comment> findByCommentId(Long parentId);

}
