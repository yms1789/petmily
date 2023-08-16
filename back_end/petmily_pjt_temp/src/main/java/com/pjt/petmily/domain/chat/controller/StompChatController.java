package com.pjt.petmily.domain.chat.controller;

import com.pjt.petmily.domain.chat.dto.ChatMessageDTO;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import com.pjt.petmily.domain.chat.repository.ChatRoomJpaRepository;
import com.pjt.petmily.domain.chat.repository.ChatRoomRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.global.FCM.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Log4j2
@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달
    @Autowired
    private com.pjt.petmily.service.ChatMessageService chatMessageService;
    @Autowired
    private ChatRoomJpaRepository chatRoomRepository;
    private FirebaseCloudMessageService firebaseCloudMessageService;


    @MessageMapping(value = "/message")
    public void message(ChatMessageDTO message) throws IOException {
        log.info("메세지 전송");
        chatMessageService.saveMessage(message);
        template.convertAndSend("/sub/room/" + message.getRoomId(), message);

        // 1. 대화방 찾기
        ChatRoom chatRoom = chatRoomRepository.findByRoomId(message.getRoomId())
                .orElseThrow(() -> new RuntimeException("대화방을 찾을 수 없음: " + message.getRoomId()));

        // 2. 메시지를 보낸 사람을 제외한 나머지 참여자들의 FCM 토큰을 가져와서 알림 전송
        for (User participant : chatRoom.getParticipants()) {
            if (!participant.getUserEmail().equals(message.getWriter())) {
                String fcmToken = participant.getFcmToken();

                if (fcmToken != null && !fcmToken.isEmpty()) {
                    String title = "새로운 메시지 알림";
                    String body = message.getWriter() + "님으로부터 새로운 메시지가 도착하였습니다.";

                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, message.getRoomId());
                }
            }
        }
    }
}