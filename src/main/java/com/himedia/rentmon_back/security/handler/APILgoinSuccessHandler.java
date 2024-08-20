package com.himedia.rentmon_back.security.handler;

import com.google.gson.Gson;
import com.himedia.rentmon_back.dto.MemberDTO;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.security.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILgoinSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberDTO memberDTO = (MemberDTO) authentication.getPrincipal();
        Map<String, Object> claims = memberDTO.getClaims();

        //엑세트 토큰 + 리프레시 토큰 생성
        String accessToken = JWTUtil.generateToken(claims,5);
        String refreshToken = JWTUtil.generateToken(claims,60*24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(claims);

        log.info("jsonStr:" + jsonStr);
        response.setContentType("application/json");

        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.flush();
    }
}
