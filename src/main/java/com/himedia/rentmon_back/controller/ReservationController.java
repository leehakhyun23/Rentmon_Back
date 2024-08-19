package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/listcount")
    public int listcount(@RequestParam("userid") String userid) {
        return reservationService.getReservation(userid);
    }

    @PostMapping("/InsertReservation")
    public HashMap<String, Object> InsertReserve(@RequestBody Reservation reservation) {
        reservationService.InsertReserve(reservation);
        return null;
    }
}
