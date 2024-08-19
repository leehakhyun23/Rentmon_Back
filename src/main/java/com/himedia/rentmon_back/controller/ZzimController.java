package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Zzim;
import com.himedia.rentmon_back.service.ZzimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/zzim")
@RequiredArgsConstructor
public class ZzimController {
    private final ZzimService zs;


    @GetMapping("/getZzimList/{userid}")
    public ResponseEntity<List<Zzim>> getZzimList(@PathVariable("userid") String userid , @RequestParam("page") int page) {
        List<Zzim> list = zs.getZzimList(userid, page);
        return  ResponseEntity.ok(list);
    }

    @PostMapping("/zzimActiveno")
    public ResponseEntity<String> zzimActiveno(@RequestParam("zseq") int zseq) {
        zs.removeZzim(zseq);
        return ResponseEntity.ok("ok");
    }
}
