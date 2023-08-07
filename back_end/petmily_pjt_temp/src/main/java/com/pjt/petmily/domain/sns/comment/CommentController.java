package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.sns.comment.dto.CommentDto;
import com.pjt.petmily.domain.sns.comment.dto.CommentParentDto;
import com.pjt.petmily.domain.sns.comment.dto.CommentRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    @Operation(summary = "대댓글 작성시 부모 ID 반환", description = "대댓글 작성하기 누르면 해당 댓글의 ID를 반환 (부모댓글이 되는 것)")
    public ResponseEntity<CommentParentDto> getParentCommentId(@PathVariable Long commentId) {
        Comment comment = commentService.findCommentById(commentId);
        return new ResponseEntity<>(CommentParentDto.fromCommentEntity(comment), HttpStatus.OK);
    }

    @PostMapping("/comment/save")
    @Operation(summary = "댓글 작성", description = "댓글 작성&저장" +
            "부모 아이디 없을 시, parentId는 null")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentService.createComment(commentRequestDto.getBoardId(),
                commentRequestDto.getUserEmail(),
                commentRequestDto.getCommentContent(),
                commentRequestDto.getParentId());
        return new ResponseEntity<>(CommentDto.fromCommentEntity(comment), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
