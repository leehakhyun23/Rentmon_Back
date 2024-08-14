package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.service.ReviewService;
import com.himedia.rentmon_back.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/getSpaceList/{page}")
    public ResponseEntity<List<Space>> getSpaceList(@PathVariable int page) {
        try{
            int size = 6;
            List<Space> spaceList = spaceService.getSpaceList(page, size);
            return ResponseEntity.ok(spaceList);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getreserve")
    public Reservation getreserve(@RequestParam("userid") String userid) {
        return spaceService.findByUserid(userid);

    }

    @GetMapping("/getSpace/{sseq}")
    public ResponseEntity<Space> getSpace(@PathVariable("sseq") int sseq) {
        try{
            Space space = spaceService.getSpace(sseq);
            return ResponseEntity.ok(space);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/insertSpace")
    public ResponseEntity<Integer> insertSpace(@RequestBody Map<String, String> space) {
        System.out.println(space.toString());
        int sseq = spaceService.insertSpace(space);
        return ResponseEntity.ok(sseq);
    }

//    @PostMapping("/insertClosed")
//    public ResponseEntity<Void> insertClosed(@RequestBody Map<String, String> closed) {
//        // Extract sseq from the closed map or some other source
//        System.out.println(closed);
//
//        int sseq = Integer.parseInt(closed.get("sseq")); // or get sseq from another source
//        System.out.println(sseq);
//
////        // Assuming you have a method to handle closed insertion
////        ss.insertClosed(sseq, closed);
//
//        return ResponseEntity.ok().build();
//    }

}
