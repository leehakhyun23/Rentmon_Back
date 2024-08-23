package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.repository.CouponRepository;
import com.himedia.rentmon_back.repository.ReservationRepository;
import com.himedia.rentmon_back.repository.ReviewRepository;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReservationService {
    private final ReservationRepository rr;
    private final ReviewRepository reviewr;
    private final CouponRepository cp;
    private final ReservationRepository reservationRepository;
    private LocalDateTime now = LocalDateTime.now();
    private final SpaceService spaceService;

    public int getReservation(String userid) {
//        List<Reservation> list= rr.findByUseridList(userid ,now);
        return 0;
    }

    public void InsertReserve(Reservation reservation) {
        rr.save(reservation);
    }

    public List<Reservation> getReservationAllList(String userid, String year, String month) {
        int parsedYear = Integer.parseInt(year);
        Month parsedMonth = Month.of(Integer.parseInt(month));

        YearMonth yearMonth = YearMonth.of(parsedYear, parsedMonth);
        LocalDateTime startTime = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endTime = yearMonth.atEndOfMonth().atTime(23, 59, 59);


        return rr.getReservationAllList(startTime, endTime, userid);
    }

    public Page<Reservation> getReservationList(String userid, PagingMj page) {
        Pageable pageable = PageRequest.of(page.getCurrentPage() - 1, page.getRecordrow());
        return rr.getReservaionListAll(userid, pageable);
    }

    public int getCountAll(String userid) {
        return rr.getCountAll(userid, now);
    }

    public int getUsedAllcount(String userid) {
        return rr.getUsedAllcount(userid, now);
    }

    public List<MypageUsedResevationDTO> getUsedList(String userid, PagingMj page) {
        List<MypageUsedResevationDTO> res = new ArrayList<>();
        Pageable pageable = PageRequest.of(page.getCurrentPage() - 1, page.getRecordrow());
        List<Reservation> reservations = rr.getUsedReservaion(userid, pageable, now).getContent();

        for (Reservation reserve : reservations) {
            MypageUsedResevationDTO mdto = new MypageUsedResevationDTO(reserve,
                    reviewr.getBooleanWrite(userid, reserve.getSpace().getSseq()),
                    reviewr.getRevewRate(userid, reserve.getSpace().getSseq()));
            res.add(mdto);
        }
        return res;
    }

    public Map<String, Object> getMypageCouponList(String userid) {
        Map<String, Object> map = new HashMap<>();
        int usewillcoupon = cp.findByUseridCount(userid, now);
        List<Coupon> coupons = cp.findByUseridWilluse(userid, now);
        map.put("list", coupons);
        map.put("count", usewillcoupon);
        return map;
    }

    public List<Reservation> getReservationListbyDate(int sseq, String date) {
        return reservationRepository.getReservationListbyDate(sseq, date);
    }

    
    public int findSseqByTitle(String title) {
        Space space = rr.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Space not found for title: " + title));
        return space.getSseq(); // int 타입 반환
    }

    public List<Reservation> findReservationsBySseq(int sseq) {
        return rr.findBySpaceSseq(sseq);
    }
}

