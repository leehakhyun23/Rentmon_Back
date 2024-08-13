package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.dto.usersnsdto.GoogleApi;
import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.entity.User;

import com.himedia.rentmon_back.security.CustomSecurityConfig;
import com.himedia.rentmon_back.service.HostService;
import com.himedia.rentmon_back.service.UserService;
import com.himedia.rentmon_back.service.UserSnsLoginService;
import com.himedia.rentmon_back.util.MailSend;
import com.himedia.rentmon_back.util.SnsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
    private final MailSend ms;

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

    @PostMapping("/join/idcheck")
    public ResponseEntity<Boolean> idCheck(@RequestParam("hostid")String hostid){
        Boolean idCheck = usersls.isHostTrue(hostid);
        return ResponseEntity.ok(idCheck);
    }

    @PostMapping("/join/mailsend")
    public ResponseEntity<String> mailSend (@RequestParam("email") String email){
        String code = usersls.mailSender(email);
        return  ResponseEntity.ok(code);
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

            Optional<Member> member = usersls.getKakaoHost(kakaoProfile);
            response.sendRedirect("http://localhost:3000/getsnshostinfo/"+member.get().getUserid());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // naver
    private String naverClinet_id="urF5LV2yokGTpt925Ejm";
    private String naverRedirect_uri ="http://localhost:8070/host/sns/naverlogin";

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
    public void loginnaver(@RequestParam("code") String code,@RequestParam("state") String state ,HttpServletResponse response){
        OAuthToken oAuthToken = usersls.getNaverToken(code,state,naverClinet_id,naverRedirect_uri);

        try {
            NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());
            Optional<Member> member = usersls.getNaverHost(naverapi);
            response.sendRedirect("http://localhost:3000/getsnshostinfo/"+member.get().getUserid()+"/naver");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

//
//    private String googleClinet_id="325806549360-rfnkgftjf45c468fs66jrf24k1o1ga8t.apps.googleusercontent.com";
//    private String gooleRedirect_uri ="http://localhost:8070/host/sns/googlelogin";
//    private String googleClientPw= "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";
//    @RequestMapping("/sns/googlestart")
//    public @ResponseBody String getgooleLogin(){
//        String a = "<script  type='text/javascript'>" +
//                "location.href='https://accounts.google.com/o/oauth2/v2/auth?"
//                +"client_id="+googleClinet_id+
//                "&redirect_uri="+gooleRedirect_uri+
//                "&response_type=code" +
//                "&scope=email profile"
//                +"';" +
//                "</script>";
//        return a;
//    }
//
//    @RequestMapping("/sns/googlelogin")
//    public void logingoole(@RequestParam("code") String code ,HttpServletResponse response) {
//        System.out.println(code);
//        OAuthToken oAuthToken = usersls.getGoogleToken(code, googleClinet_id, googleClientPw, gooleRedirect_uri);
//        GoogleApi googleapi = null;
//        try {
//            googleapi = usersls.getGoogleProfile(oAuthToken.getAccess_token());
//            Optional<Member> member = usersls.getGoogleHost(googleapi);
//            response.sendRedirect("http://localhost:3000/getsnshostinfo/" + member.get().getUserid() + "/google");
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
