package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.repository.ReservationRepository;
import com.himedia.rentmon_back.specification.AdminSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ReservationService {
    private final ReservationRepository rr;

    public int getReservation(String userid) {
        LocalDateTime now = LocalDateTime.now();
//        List<Reservation> list= rr.findByUseridList(userid ,now);
        return 0;
    }

    public void InsertReserve(Reservation reservation) {
        rr.save(reservation);
    }

    // admin
    public List<AdminDTO.ResponseReservation> getReservationsByPeriod(String period) {
        Specification<Reservation> periodSpec = AdminSpecification.AdminReservationSpe.withinPeriod(period);
        List<Reservation> reservations = rr.findAll(periodSpec);

        return reservations.stream()
                .collect(Collectors.groupingBy(
                        r -> getGroupingKey(period, r.getReservestart()),
                        Collectors.summarizingInt(Reservation::getPayment)))
                .entrySet().stream()
                .map(entry -> AdminDTO.ResponseReservation.builder()
                        .date(entry.getKey())
                        .payment((int) entry.getValue().getSum())
                        .count((int) entry.getValue().getCount())
                        .build())
                .collect(Collectors.toList());
    }

    private String getGroupingKey(String period, Timestamp timestamp) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        switch (period.toLowerCase()) {
            case "monthly":
                return dateTime.getYear() + "-" + dateTime.getMonthValue();
            case "weekly":
                return dateTime.getYear() + "-W" + dateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
            case "daily":
                return dateTime.toLocalDate().toString();
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
    }
}
