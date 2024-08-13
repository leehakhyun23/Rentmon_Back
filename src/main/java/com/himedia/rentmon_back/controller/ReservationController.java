package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService rs;

    @GetMapping("/listcount")
    public int listcount(@RequestParam("userid") String userid) {
        return rs.getReservation(userid);
    }
}
