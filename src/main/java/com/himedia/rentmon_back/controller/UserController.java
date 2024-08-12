package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.MemberService;
import com.himedia.rentmon_back.service.UserService;
import com.himedia.rentmon_back.service.UserSnsLoginService;
import com.himedia.rentmon_back.util.KakaoEception;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final MemberService ms;
    private final UserService us;
    private final UserSnsLoginService usersls;

    @GetMapping("/getuseinfo")
    public ResponseEntity<User> getUseInfo(@RequestParam("userid") String userid){
        User user = us.getUserInfo(userid);
        return ResponseEntity.ok(user);
    }

    private String kakaoclinet_id="0a261c5101ce9fc314d111962604fbae";
    private String redirect_uri ="http://localhost:8070/user/sns/kakaoLogin";

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
            if(kakaoProfile == null) throw new KakaoEception("카카오 로그인 실패" , "oAutoToken도 같이 확인해주세요.");

            System.out.println(kakaoProfile);
            Optional<Member> member = usersls.getKakaoMember(kakaoProfile);
            response.sendRedirect("http://localhost:3000/getsnsuserinfo/"+member.get().getUserid());
        } catch (KakaoEception e) {
            throw new RuntimeException(e);
        }
    }

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
        OAuthToken oAuthToken = usersls.getNaverToken(code,state,naverClinet_id,naverRedirect_uri);

        NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());

    }
}
