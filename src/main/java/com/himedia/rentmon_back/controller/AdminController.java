package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.AdminService;
import com.himedia.rentmon_back.util.CreatedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<List<User>> getUserList() {
        List<User> userList = adminService.getUserList();

        if(userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userList);
    }

    @PutMapping("/islogin")
    public ResponseEntity<Integer> isLogin(@RequestBody List<String> userids) {
        int updatedCount = adminService.updateIsLoginStatus(userids);

        return ResponseEntity.ok(updatedCount);
    }

    @PostMapping("/issuedcoupon")
    public ResponseEntity<String> createdCoupon(@RequestBody List<String> userids) {

        return ResponseEntity.ok(CreatedCoupon.generateCoupon());
    }
}
