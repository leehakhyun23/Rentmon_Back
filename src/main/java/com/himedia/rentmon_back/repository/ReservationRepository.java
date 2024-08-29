package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation> {
    @Query("SELECT r FROM Reservation r WHERE r.reservestart BETWEEN :now AND :threeHoursLater AND r.user.userid = :userid ORDER BY r.reservestart ASC")
    Page<Reservation> findReservationsWithinNext3Hours( @Param("userid") String userid ,@Param("now") LocalDateTime now, @Param("threeHoursLater") LocalDateTime threeHoursLater, Pageable pageable);

    @Query("SELECT COUNT(r) from Reservation r where r.user.userid = :userid AND r.reserveend > :now")
    Integer findByUseridCount(@Param("userid") String userid,@Param("now")  LocalDateTime now);

    @Query("select count(r) from Reservation r where r.user.userid = :userid AND r.reserveend < :now")
    Integer findByUseridWithusedCount(@Param("userid")  String userid,@Param("now")  LocalDateTime now);


    @Query("SELECT r FROM Reservation r WHERE r.user.userid = :userid AND r.reservestart >= :startTime AND r.reservestart <= :endTime")
    List<Reservation> getReservationAllList(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("userid") String userid);

    @Query("select count(r) from Reservation r where r.user.userid = :userid  AND r.reserveend > :now")
    int getCountAll(@Param("userid") String userid, @Param("now") LocalDateTime now);

    @Query(value = "SELECT * FROM reservation r " +
            "WHERE r.userid = :userid AND r.reserveend > CURRENT_DATE ORDER BY CASE WHEN r.reservestart BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '3' DAY THEN 0 " +
            " ELSE 1  END  ASC , r.reservestart desc", nativeQuery = true)
    Page<Reservation> getReservaionListAll( @Param("userid") String userid, Pageable pageable);

    @Query("select count(r) from Reservation r where r.user.userid = :userid AND r.reserveend < :now")
    int getUsedAllcount(@Param("userid") String userid , @Param("now") LocalDateTime now);

    @Query("select r from Reservation r where r.user.userid = :userid AND r.reserveend < :now")
    Page<Reservation> getUsedReservaion(String userid, Pageable pageable, LocalDateTime now);

    @Query("SELECT r FROM Reservation r WHERE r.space.sseq = :sseq AND FUNCTION('DATE', r.reservestart) = :date")
    List<Reservation> getReservationListbyDate(int sseq, String date);
  
    @Query("SELECT s FROM Space s WHERE s.title = :title")
    Optional<Space> findByTitle(@Param("title") String title);

    List<Reservation> findBySpaceSseq(int sseq);

    List<Reservation> findByReservestartBetween(Timestamp startDate, Timestamp endDate);

    Optional<Reservation> findByUserUseridAndSpaceSseq(String userid, int sseq);

    Optional<Reservation> findByUserUseridAndSpaceSseqAndReserveendBefore(String userid, int sseq, Timestamp currentTimestamp);

    // admin
//    List<Reservation> findByReservestartBetween(Timestamp startDate, Timestamp endDate);
}
