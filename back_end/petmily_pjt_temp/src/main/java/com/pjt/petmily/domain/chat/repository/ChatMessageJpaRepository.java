package com.pjt.petmily.domain.chat.repository;

import com.pjt.petmily.domain.chat.entity.ChatMessage;
import com.pjt.petmily.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoom(ChatRoom chatRoom);

    List<ChatMessage> findByChatRoomAndWriterAndIsReadFalse(ChatRoom chatRoom, String writer);

    Long countByChatRoomAndWriterAndIsReadFalse(ChatRoom chatRoom, String writer);

}