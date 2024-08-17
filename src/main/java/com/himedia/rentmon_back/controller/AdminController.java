package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.RequestCoupon;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.AdminService;
import com.himedia.rentmon_back.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final CouponService couponService;

    @GetMapping("/user")
    public ResponseEntity<Page<User>> getUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userList = adminService.getUserList(pageable);

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
    public ResponseEntity<String> createdCoupon(@RequestBody RequestCoupon.IssuedCoupon issuedCoupon) {
        couponService.createAndAssignCoupons(issuedCoupon);

        return ResponseEntity.ok(null);
    }
}
