package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository ur;
    private final CouponRepository cr;
    private final ReservationRepository rr;
    private final ZzimRepositroy zr;
    private final InquiryRepository ir;

    public User getUserInfo(String userid) {
        return ur.findByUserid(userid);
    }

    public Map<String , Integer> getMenuCount(String userid) {
        Map<String , Integer> menus = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        menus.put("couponCount",cr.findByUseridCount(userid,now));
        menus.put("reservCount",rr.findByUseridCount(userid ,now));
        menus.put("usesapceCount",rr.findByUseridWithusedCount(userid ,now));
        menus.put("zzimCount",zr.findByUseridCount(userid));
//        menus.put("inquiryCount", ir.findByUseridCount(userid));


        return menus;
    }
}
