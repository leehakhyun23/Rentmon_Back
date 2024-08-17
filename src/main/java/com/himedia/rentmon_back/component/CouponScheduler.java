package com.himedia.rentmon_back.component;

import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CouponScheduler {
    @Autowired
    private CouponRepository couponRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireCoupons() {
        List<Coupon> expiredCoupons = couponRepository.findAll()
                .stream()
                .filter(coupon -> coupon.getLimitdate().before(Timestamp.valueOf(LocalDateTime.now())))
                .collect(Collectors.toList());

        for (Coupon coupon : expiredCoupons) {
            coupon.setUseyn(false);
            couponRepository.save(coupon);
        }
    }
}
