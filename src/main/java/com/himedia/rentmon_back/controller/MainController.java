package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.dto.SpaceAndReviewRaterDTO;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService ms;

    @PostMapping("/getRecommandSpace")
    public ResponseEntity<List<SpaceAndReviewRaterDTO>> getRecommandSpace(@RequestBody(required = false) User user) {
        List<SpaceAndReviewRaterDTO> spaceList =  ms.getRecommandSpace(user);
        return ResponseEntity.ok(spaceList);
    }

    @GetMapping("/getSapceList")
    public ResponseEntity<List<SpaceAndReviewRaterDTO>> getSapceList(@RequestParam("cnum") int cnum){
        List<SpaceAndReviewRaterDTO> list = ms.getCategorySpaceList(cnum);
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/getspaceviewlist")
    public ResponseEntity<List<Space>> getspaceviewlist (@RequestBody List<Integer> rctvw){
        List<Space> list = ms.getspaceviewlist(rctvw);
        return ResponseEntity.ok(list);
    }




}
