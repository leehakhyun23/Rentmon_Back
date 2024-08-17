package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query("SELECT COUNT(c)  from Coupon c where c.user.userid = :userid AND c.useyn = true AND c.limitdate > :now")
    Integer findByUseridCount(String userid, LocalDateTime now);

    // Admin
    Optional<Coupon> findByCouponstr(String couponstr);
    boolean existsByCouponstr(String couponstr);
}
