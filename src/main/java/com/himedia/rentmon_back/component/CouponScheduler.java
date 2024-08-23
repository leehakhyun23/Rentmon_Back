package com.himedia.rentmon_back.component;

import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CouponScheduler {
    private final CouponRepository couponRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireCoupons() {
        List<Coupon> expiredCoupons = couponRepository.findAll()
                .stream()
                .filter(coupon -> coupon.getLimitdatetime().isBefore(LocalDateTime.now()))
                .toList();

        for (Coupon coupon : expiredCoupons) {
            coupon.setUseyn(false);
            couponRepository.save(coupon);
        }
    }
}
