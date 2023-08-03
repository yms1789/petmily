package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.sns.comment.dto.CommentRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {

    Comment createComment(Long boardId, String userEmail, String commentContent, Long parentId);

    void deleteComment(Long commentId);

}
