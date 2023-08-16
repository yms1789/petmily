package com.pjt.petmily.service;

import com.pjt.petmily.domain.chat.entity.ChatMessage;
import com.pjt.petmily.domain.chat.dto.ChatMessageDTO;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import com.pjt.petmily.domain.chat.repository.ChatMessageJpaRepository;
import com.pjt.petmily.domain.chat.repository.ChatRoomJpaRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.global.FCM.FirebaseCloudMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageJpaRepository chatMessageJpaRepository;

    @Autowired
    private ChatRoomJpaRepository chatRoomJpaRepository;

    @Autowired
    private FirebaseCloudMessageService firebaseCloudMessageService;

    @Transactional
    public ChatMessage saveMessage(ChatMessageDTO messageDTO) throws IOException {
        // 채팅방 검색
        ChatRoom chatRoom = chatRoomJpaRepository.findByRoomId(messageDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("채팅방이 존재 하지 않음 " + messageDTO.getRoomId()));

        // 메시지 객체 생성 및 설정
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setWriter(messageDTO.getWriter());
        chatMessage.setMessage(messageDTO.getMessage());
        chatMessage.setChatRoom(chatRoom);

        // 메시지 저장 (이 때 JPA가 ID를 생성하고 설정합니다)
        ChatMessage savedMessage = chatMessageJpaRepository.save(chatMessage);

        // chatRoom의 최신 메시지로 설정
        chatRoom.setLatestMessage(savedMessage);
        // chatRoom 업데이트
        chatRoomJpaRepository.save(chatRoom);

        // 2. 메시지를 보낸 사람을 제외한 나머지 참여자들의 FCM 토큰을 가져와서 알림 전송
        for (User participant : chatRoom.getParticipants()) {
            if (!participant.getUserEmail().equals(messageDTO.getWriter())) {
                String fcmToken = participant.getFcmToken();

                if (fcmToken != null && !fcmToken.isEmpty()) {
                    String title = "새로운 메시지 알림";
                    String body = messageDTO.getWriter() + "님으로부터 새로운 메시지가 도착하였습니다.";

                    firebaseCloudMessageService.sendMessageTo(fcmToken, title, body, messageDTO.getRoomId());
                }
            }
        }
        // 저장된 메시지 반환
        return savedMessage;
    }
}
