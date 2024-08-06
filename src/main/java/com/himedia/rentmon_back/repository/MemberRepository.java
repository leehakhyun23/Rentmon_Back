package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserid(String username);

    Optional<Object> findByUseridAndRole(String username, String role);
}