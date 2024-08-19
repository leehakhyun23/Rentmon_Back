package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Bank;
import com.himedia.rentmon_back.entity.Card;
import com.himedia.rentmon_back.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank")
@Log4j2
public class BankController {
    private final BankService bs;

    @GetMapping("/bankList")
    public List<Bank> getBankList(){
        return bs.getBankList();
    }

    @PostMapping("/changeBank")
    public ResponseEntity<String> changeBank(@RequestBody Card card ,@RequestParam("userid") String userid){
        System.out.println(card +" :  "+userid);
        bs.saveCard(card ,userid);
        return ResponseEntity.ok("ok");
    }
}
