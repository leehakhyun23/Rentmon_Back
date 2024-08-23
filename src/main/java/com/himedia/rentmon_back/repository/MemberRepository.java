package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUserid(String userid);
    Optional<Member> findByUseridAndRole(String username, String role);

    @Query("SELECT m.mseq FROM Member m WHERE m.userid = :userid")
    int findByUseridOne(@Param("userid") String userid);

    // admin
    int countByRoleNot(String role);
    int countByRole(String role);
}
