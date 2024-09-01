package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.repository.ChatMessageRepository;
import com.himedia.rentmon_back.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatMsg saveMessage(ChatMsg chatMsg) {
        return chatMessageRepository.save(chatMsg);
    }

    public void sendMessageToChatroom(int chatRoomId, ChatMsg chatMsg) {
        messagingTemplate.convertAndSend("/topic/chatroom/" + chatRoomId, chatMsg);
    }

    @Transactional
    public void processAndSendMessage(int chatRoomId, ChatMsg chatMsg) {
        ChatMsg savedMsg = saveMessage(chatMsg);

        sendMessageToChatroom(chatRoomId, savedMsg);
    }
}
