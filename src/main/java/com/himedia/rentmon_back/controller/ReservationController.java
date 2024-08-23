package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.service.CouponService;
import com.himedia.rentmon_back.service.ReservationService;
import com.himedia.rentmon_back.service.SpaceService;
import com.himedia.rentmon_back.util.PagingMj;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final CouponService cs;
    private final SpaceService ss;

    @GetMapping("/listcount")
    public int listcount(@RequestParam("userid") String userid) {
        return reservationService.getReservation(userid);
    }

    @PostMapping("/InsertReservation")
    public void InsertReservation(@RequestBody Reservation reservation) {
        reservationService.InsertReserve(reservation);
    }

    @GetMapping("/getReservationList")
    public ResponseEntity<List<Reservation>> getReservationList(@RequestParam("year") String year , @RequestParam("month") String month , @RequestParam("userid") String userid){
//        ResponseEntity<List<Reservation>> reservationList = reservationService.getReservationList();
        List<Reservation> rlist = reservationService.getReservationAllList(userid, year,month);
        return ResponseEntity.ok(rlist);
    }

    @GetMapping("/getReservationList/{userid}")
    public ResponseEntity<Map<String, Object>> getReservationList(@PathVariable("userid") String userid , @RequestParam("page") int page){
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(reservationService.getCountAll(userid));
        List<Reservation> list = reservationService.getReservationList(userid, paging).getContent();
        System.out.println(list.size()+" 리스트 개수: 전체"+ paging.getRecordAllcount());
        map.put("list", list);
        map.put("paging",paging);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getUsedReservation/{userid}")
    public ResponseEntity<Map<String, Object>> getUsedReservation(@PathVariable("userid") String userid , @RequestParam("page") int page) {
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(reservationService.getUsedAllcount(userid));
        List<MypageUsedResevationDTO> list = reservationService.getUsedList(userid, paging);
        map.put("list", list);
        map.put("paging",paging);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getMypageCouponList/{userid}")
    public ResponseEntity<Map<String, Object>> getMypageCouponList(@PathVariable("userid") String userid) {
        Map<String, Object> map = reservationService.getMypageCouponList(userid);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getMypageUsedCouponList/{userid}")
    public ResponseEntity<Map<String, Object>> getMypageUsedCouponList(@PathVariable("userid") String userid, @RequestParam("page") int page) {
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(cs.getUsedAllcount(userid));
        List<Coupon> list = cs.getUsedList(userid, paging);
        map.put("list", list);
        map.put("paging",paging);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/getReservationListbyDate")
    public ResponseEntity<Map<String, Object>> getReservationListbyDate(@RequestParam("sseq") int sseq, @RequestParam("date") String date) {
        Map<String, Object> map = new HashMap<>();
        List<Reservation> list = reservationService.getReservationListbyDate(sseq, date);
        map.put("reservationList", list);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/findSseqByTitle")
    public ResponseEntity<List<Reservation>> findSseqByTitle(@RequestParam("title") String title) {
        try {
            System.out.println("Received title: " + title);
            int sseq = reservationService.findSseqByTitle(title);
            System.out.println("Found sseq: " + sseq);
            List<Reservation> reservations = reservationService.findReservationsBySseq(sseq);
            System.out.println("Reservations: " + reservations);
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
