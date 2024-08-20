package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByUserid(String userid);

    // admin
    @Modifying // isLogin Toggle
    @Query("UPDATE User u SET u.islogin = CASE WHEN u.islogin = TRUE THEN FALSE ELSE TRUE END WHERE u.userid IN :userids")
    int updateIsLoginStatus(List<String> userids);
}
