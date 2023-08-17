package com.pjt.petmily.domain.sns.heart.controller;


import com.pjt.petmily.domain.sns.board.entity.Board;
import com.pjt.petmily.domain.sns.board.repository.BoardRepository;
import com.pjt.petmily.domain.sns.heart.dto.HeartRequestDto;
import com.pjt.petmily.domain.sns.heart.service.HeartService;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.global.FCM.FirebaseCloudMessageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.pjt.petmily.global.HttpResponseEntity.ResponseResult;

import static com.pjt.petmily.global.HttpResponseEntity.success;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final BoardRepository boardRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping(value = "/board/heart")
    @Operation(summary = "게시글 좋아요")
    public ResponseResult<?> insert(@RequestBody @Valid HeartRequestDto heartRequestDto) throws Exception {
        heartService.insert(heartRequestDto);

        // 1. 게시물 찾기
        Board board = boardRepository.findById(heartRequestDto.getBoardId())
                .orElseThrow(() -> new RuntimeException("게시글 찾을 수 없음 " + heartRequestDto.getBoardId()));
        System.out.println("게시글 찾기 완료");

        // 2. 작성자 찾기
        User boardAuthor = board.getUser();

        // 3. 작성자의 FCM 토큰 얻기
        String fcmToken = boardAuthor.getFcmToken();

        System.out.println("FCM 토큰 얻기 완료 :" + fcmToken);

        // 4. 메시지 전송
        if (fcmToken != null && !fcmToken.isEmpty()) {
            String title = "새 좋아요 알림";
            String body = boardAuthor.getUserNickname() + "님의 게시물을 좋아합니다";
            System.out.println("파이어베이스 전송");
            firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, String.valueOf(heartRequestDto.getBoardId()));
        }

        return success(null);
    }

    @DeleteMapping(value = "/board/heart")
    @Operation(summary = "게시글 좋아요 취소")
    public ResponseResult<?> delete(@RequestBody @Valid HeartRequestDto heartRequestDto) {
        heartService.delete(heartRequestDto);
        return success(null);
    }
}
