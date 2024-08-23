package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {
    @Query("SELECT COUNT(c)  from Coupon c where c.user.userid = :userid AND c.useyn = true AND c.limitdate > :now")
    Integer findByUseridCount(@Param("userid") String userid, LocalDateTime now);

    @Query("SELECT c from Coupon c where c.user.userid = :userid AND c.useyn = true AND c.limitdate > :now ORDER BY c.limitdate asc")
    List<Coupon> findByUseridWilluse(String userid, LocalDateTime now);

    @Query("SELECT COUNT(c)  from Coupon c where c.user.userid = :userid AND c.useyn = false OR c.limitdate < :now")
    int getUsedAllcount(@Param("userid") String userid, @Param("now")  LocalDateTime now);
    @Query("SELECT c  from Coupon c where c.user.userid = :userid AND c.useyn = false OR c.limitdate < :now ORDER BY c.limitdate desc ")
    Page<Coupon> getUsedList(String userid, LocalDateTime now, Pageable pageable);


//    @Query("SELECT c from Coupon c where c.userid = :userid AND c.useyn = true AND c.limitdate > :now")
//    List<Coupon> findByUserid(@Param("userid") String userid , @Param("now") LocalDateTime now );

    // Admin
    Optional<Coupon> findByCouponstr(String couponstr);
    boolean existsByCouponstr(String couponstr);
}
