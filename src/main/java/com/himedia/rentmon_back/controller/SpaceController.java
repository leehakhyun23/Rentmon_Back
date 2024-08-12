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

@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService ss;

    @GetMapping("/getSpaceList/{page}")
    public ResponseEntity<List<SpaceDTO>> getSpaceList(@PathVariable int page) {
        try{
            int size = 6;
            List<SpaceDTO> spaces = new ArrayList<>();
            spaces = ss.getSpaceList(page, size);
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
    public ResponseEntity<SpaceDTO> getSpace(@PathVariable("sseq") int sseq) {
        try{
            SpaceDTO space = ss.getSpace(sseq);
            return ResponseEntity.ok(space);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
