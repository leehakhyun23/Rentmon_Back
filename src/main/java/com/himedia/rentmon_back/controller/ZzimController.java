package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.entity.Zzim;
import com.himedia.rentmon_back.service.ZzimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/zzim")
@RequiredArgsConstructor
public class ZzimController {
    private final ZzimService zs;


    @GetMapping("/getZzimList/{userid}")
    public ResponseEntity<List<Zzim>> getZzimList(@PathVariable("userid") String userid , @RequestParam("page") int page) {
        List<Zzim> list = zs.getZzimList(userid, page);
        System.out.println("리스트 화인하겠습니다~~~~");
        System.out.println(list.size());
        return  ResponseEntity.ok(list);
    }

    @PostMapping("/zzimActiveno")
    public ResponseEntity<String> zzimActiveno(@RequestParam("zseq") int zseq) {
        zs.removeZzim(zseq);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkZzim(
            @RequestParam String userid,
            @RequestParam int sseq) {

        boolean isZzimmed = zs.checkZzim(userid, sseq);
        Map<String, Boolean> response = new HashMap<>();
        response.put("zzimOn", isZzimmed);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/toggle")
    public ResponseEntity<String> toggleZzim(
            @RequestParam String userid,
            @RequestParam int sseq) {

        zs.toggleZzim(userid, sseq);
        return ResponseEntity.ok("Zzim toggled successfully.");
    }
}
