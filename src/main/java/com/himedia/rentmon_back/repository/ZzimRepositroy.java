package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Zzim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface ZzimRepositroy extends JpaRepository<Zzim, Integer> {

    @Query("SELECT COUNT(c) from Coupon c where c.user.userid = :userid AND c.useyn = true ")
    Integer findByUseridCount(String userid);

}
