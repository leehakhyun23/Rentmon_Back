package com.himedia.rentmon_back.util;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.CouponRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class JoinCoupon {
    private final CouponRepository cr;
    private final UserRepository userRepository;


    public void joinconponFn(String userid){
        List <String> useridlist = new ArrayList<>();
        useridlist.add(userid);
        AdminDTO.RequestCoupon issuedCoupon = new AdminDTO.RequestCoupon();
        issuedCoupon.setCouponTitle("회원가입 쿠폰");
        issuedCoupon.setDiscount(3000);
        issuedCoupon.setUserids(useridlist);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime limitDateTime = now.plusDays(7);
        issuedCoupon.setLimitDateTime(limitDateTime);
        String couponCode;
        do {
            couponCode = CreatedCoupon.generateCoupon();
        } while (cr.existsByCouponstr(couponCode));

        Coupon coupon = new Coupon();
        Optional<User> user = userRepository.findById(userid);
        coupon.setUser(user.get());
        coupon.setCouponstr(couponCode);
        coupon.setCouponTitle(issuedCoupon.getCouponTitle());
        coupon.setDiscount(issuedCoupon.getDiscount());
        coupon.setLimitdate(limitDateTime);
        coupon.setUseyn(true);
        cr.save(coupon);
    }
}
