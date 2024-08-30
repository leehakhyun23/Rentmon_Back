package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, String>, JpaSpecificationExecutor<Host> {
    @Override
    Optional<Host> findById(String hostid);

//    Host getHost(String hostid);
//
//    void insertHost(Host host);
//
//    void widtDrawal(String hostid);

//    Optional<Host> findByEmail(String email);
//
//    Optional<Host> findBySnsid(String id);
//
//    Optional<Host> findByname(String name);

    Host findByHostid(String hostid);

    Optional<Host> findByNickname(String nickname);

    Optional<Host> findByHostidAndEmail(String hostid, String email);

    // admin
//    @Modifying // isLogin Toggle
//    @Query("UPDATE User u SET u.islogin = CASE WHEN u.islogin = TRUE THEN FALSE ELSE TRUE END WHERE u.userid IN :userids")
//    int updateIsLoginStatus(List<String> hostids);
}
