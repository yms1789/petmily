package com.pjt.petmily.service;

import com.pjt.petmily.domain.chat.entity.ChatMessage;
import com.pjt.petmily.domain.chat.dto.ChatMessageDTO;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import com.pjt.petmily.domain.chat.repository.ChatMessageJpaRepository;
import com.pjt.petmily.domain.chat.repository.ChatRoomJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageJpaRepository chatMessageJpaRepository;
    @Autowired
    private ChatRoomJpaRepository chatRoomJpaRepository;

    @Transactional
    public ChatMessage saveMessage(ChatMessageDTO messageDTO) {
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

        // 메시지 발신자가 현재 사용자가 아니라면 읽지 않은 메시지의 카운트를 증가
        if(!messageDTO.getWriter().equals(messageDTO.getWriter())) {
            chatRoom.incrementUnreadMessageCount();
        }

        // chatRoom 업데이트
        chatRoomJpaRepository.save(chatRoom);

        // 저장된 메시지 반환
        return savedMessage;
    }
}
