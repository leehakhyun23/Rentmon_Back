package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation> {
    @Query("SELECT r FROM Reservation r WHERE r.reservestart BETWEEN :now AND :threeHoursLater AND r.user.userid = :userid ORDER BY r.reservestart ASC")
    Page<Reservation> findReservationsWithinNext3Hours( @Param("userid") String userid ,@Param("now") LocalDateTime now, @Param("threeHoursLater") LocalDateTime threeHoursLater, Pageable pageable);

    @Query("SELECT COUNT(r) from Reservation r where r.user.userid = :userid AND r.reservestart > :now")
    Integer findByUseridCount(String userid, LocalDateTime now);

    @Query("select count(r) from Reservation r where r.user.userid = :userid AND r.reserveend < :now")
    Integer findByUseridWithusedCount(String userid, LocalDateTime now);

    // admin
    List<Reservation> findByReservestartBetween(Timestamp startDate, Timestamp endDate);
}
