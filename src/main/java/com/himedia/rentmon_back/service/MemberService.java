package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.repository.MemberRepository;
import com.himedia.rentmon_back.security.util.CustomJWTException;
import com.himedia.rentmon_back.security.util.JWTUtil;
import com.himedia.rentmon_back.util.TokenRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final HostRepository hostRepository;
    private TokenRefreshUtil trfu = new TokenRefreshUtil();


    public Map<String, Object> refreshToken(String authHeader, String refreshToken) throws CustomJWTException {

        if(refreshToken == null) throw new CustomJWTException("NULL_REFRESH");
        if(authHeader == null || authHeader.length() < 7) throw  new CustomJWTException("INVALID_HEADER");
        //추출한 내용의 7번째 글자부터 끌까지 추출
        String accessToken = authHeader.substring(7);
        if(!trfu.checkExpriedToken(accessToken)){ //기간이 지나면 true, 안지났으면 false리턴
            return Map.of("accessToken", accessToken , "refreshToken", refreshToken);
        }
        //accessToken 기간 만료시 refresh 토큰으로 재 검증
        Map<String, Object> claims = JWTUtil.validateToken(refreshToken);
        //엑세스 토큰 교체
        String newAccessToken = JWTUtil.generateToken(claims,5);
        String newRefreshToken = "";
        if( trfu.checkTime((Integer)claims.get("exp")) ) newRefreshToken = JWTUtil.generateToken(claims,60*24);
        else newRefreshToken = refreshToken;

       return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    public int insertMember(Member member){
        return memberRepository.save(member).getMseq();
    }

    public void updatepassword(String userid, String password) {
        Member member = memberRepository.findByUseridAndRole(userid, "user").get();
        PasswordEncoder ps = new BCryptPasswordEncoder();
        member.setPwd(ps.encode(password));
        memberRepository.save(member);
    }
}
