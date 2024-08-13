package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService ss;

    @GetMapping("/getSpaceList")
    public ResponseEntity<List<SpaceDTO.SpaceList>> getSpaceList() {
        try{
            List<SpaceDTO.SpaceList> spaces = new ArrayList<>();
            spaces = ss.getSpaceList();
            return ResponseEntity.ok(spaces);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getreserve")
    public Reservation getreserve(@RequestParam("userid") String userid) {
        return ss.findByUserid(userid);

    }

    @GetMapping("/getSpace/{sseq}")
    public ResponseEntity<SpaceDTO.SpaceList> getSpace(@PathVariable("sseq") int sseq) {
        try{
            SpaceDTO.SpaceList space = ss.getSpace(sseq);
            return ResponseEntity.ok(space);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/insertSpace")
    public ResponseEntity<Integer> insertSpace(@RequestBody Map<String, String> space) {
        System.out.println(space.toString());
        int sseq = ss.insertSpace(space);
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
