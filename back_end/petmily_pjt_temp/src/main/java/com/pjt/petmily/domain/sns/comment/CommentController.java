package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.sns.comment.dto.CommentRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentService.createComment(commentRequestDto.getBoardId(),
                                                        commentRequestDto.getUserEmail(),
                                                        commentRequestDto.getCommentContent(),
                                                        commentRequestDto.getParentId());
        return ResponseEntity.ok(comment);
    }
}
