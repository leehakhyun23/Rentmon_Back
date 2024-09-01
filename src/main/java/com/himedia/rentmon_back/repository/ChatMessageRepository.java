package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMsg, Integer> {
    Optional<ChatMsg> findFirstByChatroomOrderByCreatedAtDesc(ChatRoom chatRoom);
    List<ChatMsg> findAllByChatroomCrseqOrderByCreatedAtAsc(int crseq);
    List<ChatMsg> findByChatroom(ChatRoom chatroom);
    int countByChatroomAndIsReadAndSenderTypeNot(ChatRoom chatRoom, boolean isRead, String senderType);
    List<ChatMsg> findByChatroom_CrseqAndSenderTypeAndIsRead(int crseq, String user, boolean b);

    // user쪽
    @Query("SELECT COUNT(m) FROM ChatMsg m WHERE m.chatroom.crseq = :crseq AND m.senderType <> 'user' AND m.isRead = false")
    int countUnreadMessagesByCrseqAndSenderTypeNotUser(@Param("crseq") int crseq);

    @Modifying
    @Transactional
    @Query("UPDATE ChatMsg m SET m.isRead = true WHERE m.chatroom = :chatRoom AND m.senderType = 'admin' AND m.isRead = false")
    void markAdminMessagesAsRead(@Param("chatRoom") ChatRoom chatRoom);
    List<ChatMsg> findByChatroomAndIsReadFalse(ChatRoom chatRoom);
}
