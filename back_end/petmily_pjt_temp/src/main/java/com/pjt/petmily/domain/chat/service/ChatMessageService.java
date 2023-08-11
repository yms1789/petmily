package com.pjt.petmily.service;

import com.pjt.petmily.domain.chat.ChatMessage;
import com.pjt.petmily.domain.chat.ChatMessageDTO;
import com.pjt.petmily.domain.chat.ChatRoom;
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
        ChatRoom chatRoom = chatRoomJpaRepository.findByRoomId(messageDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("채팅방이 존재 하지 않음 " + messageDTO.getRoomId()));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setWriter(messageDTO.getWriter());
        chatMessage.setMessage(messageDTO.getMessage());
        chatMessage.setChatRoom(chatRoom);

        // Update the latestMessage of the chatRoom
        ChatMessage savedMessage = chatMessageJpaRepository.save(chatMessage);
        chatRoom.setLatestMessage(savedMessage);
        chatRoomJpaRepository.save(chatRoom);


        return chatMessageJpaRepository.save(chatMessage);
    }
}
