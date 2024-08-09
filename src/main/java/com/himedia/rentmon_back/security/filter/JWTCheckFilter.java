package com.himedia.rentmon_back.security.filter;

import com.google.gson.Gson;
import com.himedia.rentmon_back.dto.MemberDTO;
import com.himedia.rentmon_back.security.util.CustomJWTException;
import com.himedia.rentmon_back.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeaderStr = request.getHeader("Authorization");

        try {
            String accessToken = authHeaderStr.substring(7);
//            Map<String, Object> claims = new HashMap<>();
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            int mseq = (int)claims.get("mseq");
            String userid = (String) claims.get("userid");
            String pwd = (String) claims.get("pwd");
            String email = (String) claims.get("email");
            String nickname = (String) claims.get("nickname");
            String phone = (String) claims.get("phone");
            String snsid = (String) claims.get("snsid");
            String provider = (String) claims.get("provider");
            String profileimg = (String) claims.get("profileimg");
            String profilemsg = (String) claims.get("intro");

            String role = (String) claims.get("role");
            Timestamp createAt = (Timestamp) claims.get("create_at");


            MemberDTO memberDTO = new MemberDTO( userid , pwd , createAt , role , mseq );

            log.info(memberDTO);
            log.info(memberDTO.getAuthorities());

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO , pwd, memberDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        } catch (CustomJWTException e) {
            log.error("JWT Check Error..............");
            log.error(e.getMessage());
            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("check uri..."+path);
        if(request.getMethod().equals("OPTIONS")) return true;
        if(path.startsWith("/member/login")) return true;
        //리프레쉬 요청
        if(path.startsWith("/member/refresh"))return true;

        //유저 sns 로그인
        if(path.startsWith("/user/sns"))return true;
        if(path.startsWith("/space/getSpaceList")) return true;
        if(path.startsWith("/space")) return true;
        if(path.startsWith("/space_images")) return true;
        if(path.startsWith("/favicon.ico")) return true;


        return false;
    }
}
