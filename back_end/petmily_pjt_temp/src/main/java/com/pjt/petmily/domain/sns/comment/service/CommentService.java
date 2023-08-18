package com.pjt.petmily.domain.sns.comment.service;

import com.pjt.petmily.domain.sns.comment.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    Comment createComment(Long boardId, String userEmail, String commentContent, Long parentId);

    Comment findCommentById(Long commentId);

    void deleteComment(Long commentId);

}
