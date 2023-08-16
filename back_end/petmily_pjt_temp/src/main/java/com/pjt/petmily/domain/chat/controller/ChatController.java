package com.pjt.petmily.domain.chat.controller;

import com.pjt.petmily.domain.chat.dto.ChatHistoryDto;
import com.pjt.petmily.domain.chat.entity.ChatMessage;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import com.pjt.petmily.domain.chat.dto.ChatRequestDto;
import com.pjt.petmily.domain.chat.dto.ChatRoomDTO;
import com.pjt.petmily.domain.chat.repository.ChatRoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class ChatController {

    @Autowired
    private com.pjt.petmily.service.ChatRoomService chatRoomService;

    //채팅방 목록 조회
    @GetMapping(value = "/{userEmail}")
    @Operation(summary = "특정 유저의 채팅방 목록 조회")
    public ResponseEntity<List<ChatHistoryDto>> userRooms(@PathVariable String userEmail){

        log.info("# Chat Rooms for user: " + userEmail);

        try {
            List<ChatHistoryDto> userChatRoomsInfo = chatRoomService.getUserChatRoomsInfo(userEmail);
            return new ResponseEntity<>(userChatRoomsInfo, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/start")
    @Operation(summary = "채팅방 생성", description = "chatRequestDto에 sender, receiver 보내면 채팅방이 개설됨")
    public ResponseEntity<?> createChat(@RequestBody ChatRequestDto chatRequestDto) {
        log.info("# Create Chat Room for user email: " + chatRequestDto.getReceiver());

        try {
            ChatRoomDTO createdRoom = chatRoomService.createChatRoom(chatRequestDto);
            return ResponseEntity.ok(createdRoom.getRoomId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("채팅방 개설 실패 " + e.getMessage());
        }
    }


    //채팅방 조회
    @PostMapping("/history")
    @Operation(summary = "채팅방 조회", description = "채팅 내역 조회")
    public ResponseEntity<?> getChatHistory(@RequestBody ChatRequestDto chatRequestDto) {
        log.info("# Get Chat History for " + chatRequestDto.getSender() + " and " + chatRequestDto.getReceiver());
        try {
            List<ChatMessage> messages = chatRoomService.getChatHistory(chatRequestDto);

            // messages가 null이거나 비어있는 경우 빈 배열 반환
            if (messages == null || messages.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            log.info(messages.toString());
            return ResponseEntity.ok(messages);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}