package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

//    @Query("SELECT r FROM Reservation r WHERE r.userid = :userid AND r.reservestart BETWEEN :now AND :threeHoursLater ORDER BY r.reservestart ASC")
//    Page<Reservation> findReservationsWithinNext3Hours(@Param("userid") String userid, @Param("now") LocalDateTime now, @Param("threeHoursLater") LocalDateTime threeHoursLater, Pageable pageable);

}
