package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.dto.AdminHostDTO;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
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
}
