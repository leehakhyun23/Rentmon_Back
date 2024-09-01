package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chatroom/{crseq}/send")
    public void sendMessage(@Payload ChatMsg chatMsg, @DestinationVariable int crseq) {
        chatMsg.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        chatService.processAndSendMessage(crseq, chatMsg);
    }
}
