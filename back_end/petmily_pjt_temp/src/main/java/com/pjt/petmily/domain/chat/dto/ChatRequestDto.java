package com.pjt.petmily.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRequestDto {
    private String sender;
    private String receiver;
}
