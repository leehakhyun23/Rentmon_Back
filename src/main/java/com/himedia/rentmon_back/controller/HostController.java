package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.security.CustomSecurityConfig;
import com.himedia.rentmon_back.service.HostService;
import com.himedia.rentmon_back.service.UserService;
import com.himedia.rentmon_back.service.UserSnsLoginService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/host")
@RequiredArgsConstructor
public class HostController {
    private final HostService hs;
    private final UserService us;
    private final UserSnsLoginService usersls;
    private final CustomSecurityConfig cc;

    @GetMapping("/gethostinfo")
    public ResponseEntity<Host> getHostInfo(@RequestParam("hostid") String hostid){
        Host host = hs.getHostInfo(hostid);
        return ResponseEntity.ok(host);
    }

    @PostMapping("/join")
    public Map<String, Object> join( @RequestBody Host host){
        Map<String, Object> result = new HashMap<String, Object>();
        PasswordEncoder pe = cc.passwordEncoder();
        host.setPwd( pe.encode(host.getPwd()) );
        hs.insertMember(host);
        result.put("msg", "ok");
        return result;
    }

    private String kakaoclinet_id="af83d61f2710ca5e0a91faa716b35322";
    private String redirect_uri ="http://localhost:8070/host/sns/kakaoLogin";

    @RequestMapping("/sns/kakaostart")
    public @ResponseBody String getKakaoLogin(){
        String a = "<script  type='text/javascript'>" +
                "location.href='https://kauth.kakao.com/oauth/authorize?"
                +"client_id="+kakaoclinet_id
                +"&redirect_uri="+redirect_uri
                +"&response_type=code';" +
                "</script>";
        return a;
    }

    @RequestMapping("/sns/kakaoLogin")
    public void loginkakao(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        OAuthToken oAuthToken = usersls.getKakaoToken(code,kakaoclinet_id,redirect_uri);
        KakaoProfile kakaoProfile = usersls.getKakaoProfile(oAuthToken);
        try {
//            if(kakaoProfile == null) throw new KakaoEception("카카오 로그인 실패" , "oAutoToken도 같이 확인해주세요.");
            Optional<Member> member = usersls.getKakaoHost(kakaoProfile);
            response.sendRedirect("http://localhost:3000/getsnshostinfo/"+member.get().getUserid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // naver
    private String naverClinet_id="91PQCpHwsJKS6Ni5owXS";
    private String naverRedirect_uri ="http://localhost:8070/user/sns/naverlogin";

    @RequestMapping("/sns/naverstart")
    public @ResponseBody String getnaverLogin(){
        String a = "<script  type='text/javascript'>" +
                "location.href='https://nid.naver.com/oauth2.0/authorize?response_type=code&"
                +"client_id="+naverClinet_id
                +"&state=STATE_STRING" +
                "&redirect_uri="+naverRedirect_uri
                +"';" +
                "</script>";
        return a;
    }

    @RequestMapping("/sns/naverlogin")
    public void loginnaver(@RequestParam("code") String code,@RequestParam("state") String state ,HttpServletResponse response) throws IOException {
        OAuthToken oAuthToken = usersls.getNaverToken(code, state, naverClinet_id, naverRedirect_uri);

        NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());
    }
}
