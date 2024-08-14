package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT COUNT(c)  from Coupon c where c.user.userid = :userid AND c.useyn = true AND c.limitdate > :now")
    Integer findByUseridCount(String userid, LocalDateTime now);

//    @Query("SELECT c from Coupon c where c.userid = :userid AND c.useyn = true AND c.limitdate > :now")
//    List<Coupon> findByUserid(@Param("userid") String userid , @Param("now") LocalDateTime now );

}
