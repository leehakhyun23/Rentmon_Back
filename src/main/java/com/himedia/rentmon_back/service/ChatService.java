package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.ChatInfoDto;
import com.himedia.rentmon_back.dto.MessageRequest;
import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.entity.ChatRoom;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.ChatMessageRepository;
import com.himedia.rentmon_back.repository.ChatRoomRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    @Transactional
    public ChatMsg saveMessage(ChatMsg chatMsg) {
        return chatMessageRepository.save(chatMsg);
    }

    @Transactional
    public void processAndSendMessage(int crseq, ChatMsg chatMsg) {
        saveMessage(chatMsg);
        messagingTemplate.convertAndSend("/topic/chatroom/" + crseq, chatMsg);
    }

    public ChatMsg sendMessage(MessageRequest request) {
        User user = userRepository.findById(request.getUserid())
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatRoom chatRoom = chatRoomRepository.findByUser(user)
                .orElseGet(() -> {
                    // 존재하지 않으면 ChatRoom 생성
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.setUser(user);
                    newChatRoom.setNickName(user.getName());
                    newChatRoom.setStatus(true);
                    return chatRoomRepository.save(newChatRoom);
                });

        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setChatroom(chatRoom);
        chatMsg.setSenderType(request.getSenderType());
        chatMsg.setMessage(request.getMessage());
        chatMsg.setRead(false);

        return chatMessageRepository.save(chatMsg);
    }

    public List<ChatMsg> getChatMessages(String userid) {
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUser(user);

        if (chatRoom.isPresent()) {
            chatMessageRepository.markAdminMessagesAsRead(chatRoom.get());

            return chatMessageRepository.findByChatroom(chatRoom.get());
        } else {
            return List.of();
        }
    }

    public ChatInfoDto getChatInfo(String userid) {
        Optional<Integer> optionalCrseq = chatRoomRepository.findCrseqByUserId(userid);
        Integer crseq = optionalCrseq.orElse(null);

        if (crseq == null) {
            ChatRoom newChatRoom = new ChatRoom();
            newChatRoom.setUser(userRepository.findById(userid).orElseThrow(() -> new IllegalArgumentException("Invalid user ID")));
            newChatRoom.setNickName(userid);
            ChatRoom savedChatRoom = chatRoomRepository.save(newChatRoom);
            crseq = savedChatRoom.getCrseq();
        }

        int unreadMessages = chatMessageRepository.countUnreadMessagesByCrseqAndSenderTypeNotUser(crseq);

        return new ChatInfoDto(crseq, unreadMessages);
    }


    public void markMessagesAsRead(int crseq) {
        ChatRoom chatRoom = chatRoomRepository.findById(crseq)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        List<ChatMsg> unreadMessages = chatMessageRepository.findByChatroomAndIsReadFalse(chatRoom);

        for (ChatMsg msg : unreadMessages) {
            msg.setRead(true);
        }

        chatMessageRepository.saveAll(unreadMessages);
    }
}
