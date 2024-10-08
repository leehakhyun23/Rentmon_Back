package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.entity.Zzim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZzimRepositroy extends JpaRepository<Zzim, Integer> {

    @Query("SELECT COUNT(z) from Zzim z where z.user.userid = :userid")
    Integer findByUseridCount(String userid);

    @Query("SELECT z from Zzim z where z.user.userid = :userid order by z.zseq desc ")
    Page<Zzim> getZzimList(String userid, Pageable pageable);

    @Query("select count(z) from Zzim z where z.space.sseq = :sseq ")
    int getAllZzimCount(@Param("sseq") int sseq);

    boolean existsByUser_UseridAndSpace_Sseq(String userId, int sseq);

    boolean existsByUserAndSpace(User user, Space space);

    void deleteByUserAndSpace(User user, Space space);
}
