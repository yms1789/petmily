package com.pjt.petmily.domain.chat.repository;

import com.pjt.petmily.domain.chat.ChatMessage;
import com.pjt.petmily.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);

}