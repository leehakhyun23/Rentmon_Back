package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMsg, Integer> {
    Optional<ChatMsg> findFirstByChatroomOrderByCreatedAtDesc(ChatRoom chatRoom);
    List<ChatMsg> findAllByChatroomCrseqOrderByCreatedAtAsc(int crseq);
}
