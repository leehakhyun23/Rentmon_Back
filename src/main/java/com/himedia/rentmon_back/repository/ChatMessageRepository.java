package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.ChatMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMsg, Integer> {
}
