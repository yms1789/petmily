package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.BoardRepository;
import com.pjt.petmily.domain.sns.comment.dto.*;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.FCM.FirebaseCloudMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final BoardRepository boardRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @GetMapping("/{commentId}")
    @Operation(summary = "대댓글 작성시 부모 ID 반환", description = "대댓글 작성하기 누르면 해당 댓글의 ID를 반환 (부모댓글이 되는 것)")
    public ResponseEntity<CommentParentDto> getParentCommentId(@PathVariable Long commentId) {
        Comment comment = commentService.findCommentById(commentId);
        return new ResponseEntity<>(CommentParentDto.fromCommentEntity(comment), HttpStatus.OK);
    }

    @PostMapping("/comment/save")
    @Operation(summary = "댓글 작성", description = "댓글 작성&저장" +
            "부모 아이디 없을 시, parentId는 null")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentRequestDto commentRequestDto) throws IOException {
        Comment comment = commentService.createComment(commentRequestDto.getBoardId(),
                commentRequestDto.getUserEmail(),
                commentRequestDto.getCommentContent(),
                commentRequestDto.getParentId());

        // 1. 게시물 찾기
        Board board = boardRepository.findById(commentRequestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글 찾을 수 없음 " + commentRequestDto.getBoardId()));
        System.out.println("게시글 찾기 완료");
        // 2. 작성자 찾기
        User boardAuthor = board.getUser();
        // 3. 작성자의 FCM 토큰 얻기
        String fcmToken = boardAuthor.getFcmToken();

        System.out.println("FCM 토큰 얻기 완료 :" + fcmToken);
        // 4. 메시지 전송
        if (fcmToken != null && !fcmToken.isEmpty()) {
            String title = "새 댓글 알림";
            String body = boardAuthor.getUserNickname() + "님의 게시물에 새 댓글이 작성되었습니다";
            System.out.println("파이어베이스 전송");
            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, commentRequestDto.getBoardId());
        }
        return new ResponseEntity<>(CommentDto.fromCommentEntity(comment), HttpStatus.OK);
    }

    @PostMapping("/comment/wsave/")
    @Operation(summary = "댓글 작성", description = "댓글 작성&저장" +
            "부모 아이디 없을 시, parentId는 null")
    public ResponseEntity<CommentWebDto> createComment(@RequestBody CommentWebRequestDto commentWebRequestDto) {
        Comment comment = commentService.createComment(commentWebRequestDto.getBoardId(),
                commentWebRequestDto.getUserEmail(),
                commentWebRequestDto.getCommentContent(),
                commentWebRequestDto.getParentId());
        return new ResponseEntity<>(CommentWebDto.fromCommentEntity(comment), HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
