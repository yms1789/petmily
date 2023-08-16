package com.pjt.petmily.domain.chat.controller;

import com.pjt.petmily.domain.chat.dto.ChatMessageDTO;
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


    @MessageMapping(value = "/message")
    public void message(ChatMessageDTO message) throws IOException {
        log.info("메세지 전송");
        chatMessageService.saveMessage(message);
        template.convertAndSend("/sub/room/" + message.getRoomId(), message);
    }
}