package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.SpaceDTO;
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
    public ResponseEntity<List<SpaceDTO.SpaceList>> getSpaceList(@RequestParam(value="word", required = false) String word) {
        try{
            List<SpaceDTO.SpaceList> spaces = new ArrayList<>();

            spaces = ss.getSpaceList();


            //spaces에다가 먼저 Space를 조회해 값을 넣어준다.

            //spaces에다가 먼저 images를 조회해 값을 넣어준다.
            //spaces에다가 먼저 hashtags를 조회해 값을 넣어준다.

            //spaces에다가 찜 개수를 세서 넣어준다
            //spaces에다가




            return ResponseEntity.ok(spaces);
        } catch (Exception e){
            e.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

}
