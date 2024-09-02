package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.MessageRequest;
import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chatroom/{crseq}/send")
    public void sendMessage(@Payload ChatMsg chatMsg, @DestinationVariable int crseq) {
        chatMsg.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        chatService.processAndSendMessage(crseq, chatMsg);
    }

    @PostMapping("/sendMessage")
    public ChatMsg sendMessage(@RequestBody MessageRequest request) {
        return chatService.sendMessage(request);
    }

    @GetMapping("/chatroom/{userid}")
    public List<ChatMsg> getChatMessages(@PathVariable String userid) {
        return chatService.getChatMessages(userid);
    }

    @PostMapping("/markAsRead/{crseq}")
    public void markMessagesAsRead(@PathVariable int crseq) {
        chatService.markMessagesAsRead(crseq);
    }
}
