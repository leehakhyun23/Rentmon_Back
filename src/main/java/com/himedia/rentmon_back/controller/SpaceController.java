package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

}
