package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.ChatRoom;
import com.himedia.rentmon_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
//    List<ChatRoom> findAllByOrderByCreatedAtDesc();
    Optional<ChatRoom> findByUser(User user);

    // user쪽
    @Query("SELECT c.crseq FROM ChatRoom c WHERE c.user.userid = :userid")
    int findCrseqByUserId(String userid);
}
