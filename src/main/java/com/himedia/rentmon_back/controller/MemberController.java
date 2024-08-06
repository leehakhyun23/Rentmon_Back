package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.LoginRequest;
import com.himedia.rentmon_back.security.service.CustomSecurityService;
import com.himedia.rentmon_back.security.util.CustomJWTException;
import com.himedia.rentmon_back.security.util.JWTUtil;
import com.himedia.rentmon_back.service.MemberService;
import com.himedia.rentmon_back.util.TokenRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final CustomSecurityService customSecurityService;
    private final MemberService ms;


    @GetMapping("/refresh/{refreshToken}")
    public Map<String, Object> refresh( @RequestHeader("Authorization") String authHeader, @PathVariable("refreshToken") String refreshToken) throws CustomJWTException {
        return ms.refreshToken(authHeader , refreshToken);
    }


}
