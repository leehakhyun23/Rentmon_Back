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
