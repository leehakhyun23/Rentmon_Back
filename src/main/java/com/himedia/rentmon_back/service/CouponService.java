package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.CouponRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import com.himedia.rentmon_back.util.CreatedCoupon;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CouponService {
    private final CouponRepository cr;
    private final UserRepository userRepository;
    private LocalDateTime now = LocalDateTime.now();

    public int getUsedAllcount(String userid) {
        return cr.getUsedAllcount(userid , now);
    }

    public List<Coupon> getUsedList(String userid, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage() - 1, paging.getRecordrow());

        Page<Coupon> list = cr.getUsedList(userid, now, pageable);
        return list.getContent();
    }

    public Coupon useCoupon(String userid, String couponstr) {
        Optional<Coupon> couponOpt = cr.findByCouponstr(couponstr);

        String message = "";

        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();
//            if (coupon.getUser().getUserid() != userid){
//                message="당신은 해당 쿠폰이 없습니다.";
//                throw new IllegalArgumentException("당신은 해당 쿠폰이 없습니다.");
//            }

            if (coupon.getLimitdate().isBefore(LocalDateTime.now())) {
                message="쿠폰의 유효기간이 지났습니다.";
                throw new IllegalArgumentException("쿠폰의 유효기간이 지났습니다.");
            }

            if (!coupon.isUseyn()) {
                message= "이 쿠폰은 이미 사용되었습니다.";
                throw new IllegalArgumentException("이 쿠폰은 이미 사용되었습니다.");
            }

            message="쿠폰을 사용합니다";
            coupon.setUseyn(false);
            cr.save(coupon);
            return coupon;
        }
        message="쿠폰이 없습니다";

        return null;
    }

    public void createAndAssignCoupons(AdminDTO.RequestCoupon issuedCoupon) {
        for (String userid : issuedCoupon.getUserids()) {
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다: " + userid));

            String couponCode;
            do {
                couponCode = CreatedCoupon.generateCoupon();
            } while (cr.existsByCouponstr(couponCode));

            Coupon coupon = new Coupon();
            coupon.setUser(user);
            coupon.setCouponstr(couponCode);
            coupon.setCouponTitle(issuedCoupon.getCouponTitle());
            coupon.setDiscount(issuedCoupon.getDiscount());
            coupon.setLimitdate(issuedCoupon.getLimitDateTime());
            coupon.setUseyn(true);

            cr.save(coupon);
        }
    }
}
