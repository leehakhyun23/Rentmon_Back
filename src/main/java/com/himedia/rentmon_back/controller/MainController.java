package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService ms;

    @PostMapping("/getRecommandSpace")
    public ResponseEntity<List<Space>> getRecommandSpace(@RequestBody(required = false) User user) {
        List<Space> spaceList =  ms.getRecommandSpace(user);
        return ResponseEntity.ok(spaceList);
    }



}
