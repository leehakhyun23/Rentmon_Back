package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.UserRepository;
import com.himedia.rentmon_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService us;

    @GetMapping("/getuseinfo")
    public ResponseEntity<User> getUseInfo(@RequestParam("userid") String userid){
        User user = us.getUserInfo(userid);
        if(user == null) ResponseEntity.badRequest().build();
        return ResponseEntity.ok(user);
    }
}
