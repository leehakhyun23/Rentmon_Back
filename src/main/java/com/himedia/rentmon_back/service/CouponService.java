package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.repository.CouponRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class CouponService {
    private final CouponRepository cr;
    private LocalDateTime now = LocalDateTime.now();

    public int getUsedAllcount(String userid) {
        return cr.getUsedAllcount(userid , now);
    }

    public List<Coupon> getUsedList(String userid, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage()-1 , paging.getRecordrow());

        Page<Coupon> list = cr.getUsedList(userid, now ,pageable);
        return list.getContent();
    }
}


    private final UserRepository userRepository;

    public Optional<Coupon> useCoupon(String couponstr) {
        Optional<Coupon> couponOpt = couponRepository.findByCouponstr(couponstr);

        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();

            if (coupon.getLimitdate().isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("쿠폰의 유효기간이 지났습니다.");
            }

            if (coupon.isUseyn()) {
                throw new IllegalArgumentException("이 쿠폰은 이미 사용되었습니다.");
            }

            coupon.setUseyn(false);
            couponRepository.save(coupon);
            return Optional.of(coupon);
        }

        return Optional.empty();
    }

    public void createAndAssignCoupons(AdminDTO.RequestCoupon issuedCoupon) {
        for (String userid : issuedCoupon.getUserids()) {
            User user = userRepository.findById(userid)
                    .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다: " + userid));

            String couponCode;
            do {
                couponCode = CreatedCoupon.generateCoupon();
            } while (couponRepository.existsByCouponstr(couponCode));

            Coupon coupon = new Coupon();
            coupon.setUser(user);
            coupon.setCouponstr(couponCode);
            coupon.setCouponTitle(issuedCoupon.getCouponTitle());
            coupon.setDiscount(issuedCoupon.getDiscount());
            coupon.setLimitdate(issuedCoupon.getLimitDate());
            coupon.setUseyn(true);

            couponRepository.save(coupon); // 쿠폰 저장
        }
    }
}
