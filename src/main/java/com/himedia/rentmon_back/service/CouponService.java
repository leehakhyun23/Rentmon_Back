package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public Optional<Coupon> useCoupon(String couponstr) {
        Optional<Coupon> couponOpt = couponRepository.findByCouponstr(couponstr);

        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();

            if (coupon.getLimitdate().before(Timestamp.valueOf(LocalDateTime.now()))) {
                throw new IllegalArgumentException("쿠폰의 유효기간이 지났습니다.");
            }

            if (coupon.isUseyn()) {
                throw new IllegalArgumentException("이 쿠폰은 이미 사용되었습니다.");
            }

            coupon.setUseyn(false); // 사용했다는 거
            couponRepository.save(coupon);
            return Optional.of(coupon);
        }

        return Optional.empty();
    }
}
