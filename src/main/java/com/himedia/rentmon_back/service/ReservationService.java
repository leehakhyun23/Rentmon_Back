package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReservationService {
    private final ReservationRepository rr;

    public int getReservation(String userid) {
        LocalDateTime now = LocalDateTime.now();
//        List<Reservation> list= rr.findByUserid(userid ,now);
        return 0;
    }
}
