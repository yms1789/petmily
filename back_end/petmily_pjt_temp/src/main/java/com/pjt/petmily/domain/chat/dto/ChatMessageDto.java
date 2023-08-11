package com.pjt.petmily.domain.chat.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageDto {
//    public enum MessageType{
//        ENTER, TALK
//    }
//
//    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
