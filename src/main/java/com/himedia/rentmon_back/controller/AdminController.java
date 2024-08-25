package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.service.*;
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
    private final MemberService memberService;
    private final CouponService couponService;
    private final SpaceService spaceService;
    private final ReservationService reservationService;

    @GetMapping("/main")
    public ResponseEntity<AdminDTO.ResponseDashBoard> getMainInfo(@RequestParam String period) {
        List<AdminDTO.ResponseCategory> category = spaceService.findAll();
        AdminDTO.ResponseMember memberStatistics = memberService.getMemberStatistics();
        List<AdminDTO.ResponseReservation> reservations = reservationService.getReservationsByPeriod(period);

        AdminDTO.ResponseDashBoard dashBoard = AdminDTO.ResponseDashBoard.builder()
                .category(category)
                .member(memberStatistics)
                .reservation(reservations)
                .build();

        return ResponseEntity.ok(dashBoard);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<AdminDTO.ResponseUser>> getUserList(@RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size,
                                                                   @RequestParam(value = "searchType", required = false) String searchType,
                                                                   @RequestParam(value = "keyword", required = false) String keyword,
                                                                   @RequestParam(value = "isLogin", required = false) Boolean isLogin,
                                                                   @RequestParam(value = "sortByDeclasCount", required = false) Boolean sortByDeclasCount) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminDTO.ResponseUser> userList = adminService.getUserList(pageable, searchType, keyword, isLogin, sortByDeclasCount);

        if (userList.isEmpty()) {
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
    public ResponseEntity<String> createdCoupon(@RequestBody AdminDTO.RequestCoupon issuedCoupon) {
        couponService.createAndAssignCoupons(issuedCoupon);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/coupon")
    public ResponseEntity<Page<AdminDTO.ResponseCoupon>> getCouponList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) Boolean useyn) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("limitdate").ascending());
        Page<AdminDTO.ResponseCoupon> couponList;

        if (useyn != null) {
            couponList = adminService.getCouponListByUseyn(pageable, useyn);
        } else {
            couponList = adminService.getCouponList(pageable);
        }

        if(couponList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(couponList);
    }

    @GetMapping("/host")
    public ResponseEntity<Page<AdminDTO.ResponseHost>> getHostList(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "searchType", required = false) String searchType, @RequestParam(value = "keyword", required = false) String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("hostid").ascending());
        Page<AdminDTO.ResponseHost> hostList = adminService.getHostList(pageable, searchType, keyword);

        if (hostList.isEmpty()) {
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

    @GetMapping("/declaration")
    public ResponseEntity<AdminDTO.ResponseDeclaration> getDeclarationList(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam String tab, @RequestParam(required = false) String reply) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "dseq"));
        AdminDTO.ResponseDeclaration declarationList;

        if ("userSpace".equals(tab)) {
            declarationList = adminService.getUserSpaceDeclarations(pageable, reply);
        } else if ("hostUser".equals(tab)) {
            declarationList = adminService.getHostUserDeclarations(pageable, reply);
        } else {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(declarationList);
    }

    @GetMapping("/declarationview/{dseq}")
    public ResponseEntity<Declaration> getDeclaration(@PathVariable int dseq) {
        return adminService.getDeclarationById(dseq)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/declaration")
    public ResponseEntity<Declaration> createDeclarationReply(@RequestBody AdminDTO.ReqeustDeclarationReply request) {
        Declaration updatedDeclaration = adminService.saveReply(request);

        if (updatedDeclaration == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedDeclaration);
    }
}
