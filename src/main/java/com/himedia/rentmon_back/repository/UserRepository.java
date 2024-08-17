package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserid(String userid);

    @Modifying
    @Query("UPDATE User u SET u.islogin = CASE WHEN u.islogin = TRUE THEN FALSE ELSE TRUE END WHERE u.userid IN :userids")
    int updateIsLoginStatus(List<String> userids);
}
