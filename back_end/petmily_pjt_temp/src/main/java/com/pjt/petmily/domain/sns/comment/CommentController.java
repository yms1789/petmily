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

    @PostMapping("/comment/save")
    @Operation(summary = "댓글 저장", description = "댓글 작성 후 저장")
    public ResponseEntity<String> commentSave(@RequestBody CommentRequestDto commentRequestDto){
        commentService.saveComment(commentRequestDto);

        return null;
    }
}
