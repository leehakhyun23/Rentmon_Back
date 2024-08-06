package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final AdminService adminservice;

    @GetMapping("/user")
    public ResponseEntity<List<AdminDTO.ResponseUser>> getUserList() {
        try {
            List<AdminDTO.ResponseUser> userList = new ArrayList<>();

            return ResponseEntity.ok(userList);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/host")
    public ResponseEntity<List<AdminDTO.ResponseHost>> getHostList() {
        try {
            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.badRequest().build();
        }
    }
}
