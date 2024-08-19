package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Zzim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ZzimRepositroy extends JpaRepository<Zzim, Integer> {

    @Query("SELECT COUNT(z) from Zzim z where z.user.userid = :userid")
    Integer findByUseridCount(String userid);

    @Query("SELECT z from Zzim z where z.user.userid = :userid order by z.zseq desc ")
    Page<Zzim> getZzimList(String userid, Pageable pageable);
}
