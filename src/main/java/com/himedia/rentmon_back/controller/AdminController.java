package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Coupon;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.service.AdminService;
import com.himedia.rentmon_back.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<AdminDTO.ResponseUser>> getUserList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "searchType", required = false) String searchType, @RequestParam(value = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<AdminDTO.ResponseUser> userList = adminService.getUserList(pageable, searchType, keyword);

        if(userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userList);
    }

    @PutMapping("/islogin")
    public ResponseEntity<Integer> userIsLogin(@RequestBody List<String> userids) {
        int updatedCount = adminService.updateUserIsLoginStatus(userids);

        return ResponseEntity.ok(updatedCount);
    }

    @PostMapping("/issuedcoupon")
    public ResponseEntity<String> createdCoupon(@RequestBody AdminDTO.IssuedCoupon issuedCoupon) {
        couponService.createAndAssignCoupons(issuedCoupon);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/coupon")
    public ResponseEntity<Page<Coupon>> getCouponList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Coupon> couponList = adminService.getCouponList(pageable);

        if(couponList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(couponList);
    }

    @GetMapping("/host")
    public ResponseEntity<Page<AdminDTO.ResponseHost>> getHostList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "searchType", required = false) String searchType, @RequestParam(value = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("member.createdAt").descending());
        Page<AdminDTO.ResponseHost> hostList = adminService.getHostList(pageable, searchType, keyword);

        if(hostList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(hostList);
    }

//    @PutMapping("/host")
//    public ResponseEntity<Integer> hostIsLogin(@RequestBody List<String> hostids) {
//        int updatedCount = adminService.updateHostIsLoginStatus(hostids);
//
//        return ResponseEntity.ok(updatedCount);
//    }
}
