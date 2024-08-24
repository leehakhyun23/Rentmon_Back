package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.usersnsdto.GoogleApi;
import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.security.CustomSecurityConfig;
import com.himedia.rentmon_back.service.HostService;
import com.himedia.rentmon_back.service.MemberService;
import com.himedia.rentmon_back.service.UserService;
import com.himedia.rentmon_back.service.UserSnsLoginService;
import com.himedia.rentmon_back.util.MailSend;
import com.himedia.rentmon_back.util.SnsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
    private final MemberService ms;
    private final HostService hs;
    private final UserService us;
    private final UserSnsLoginService usersls;
    private final CustomSecurityConfig cc;
    private final MailSend mailSend;

    @GetMapping("/gethostinfo")
    public ResponseEntity<Host> getHostInfo(@RequestParam("hostid") String hostid){
        Host host = hs.getHostInfo(hostid);
        System.out.println(host.toString());
        return ResponseEntity.ok(host);
    }


//    @PostMapping("/join")
//    public Map<String, Object> join( @RequestBody Host host){
//        Map<String, Object> result = new HashMap<String, Object>();
//        PasswordEncoder pe = cc.passwordEncoder();
//        host.setPwd( pe.encode(host.getPwd()) );
//        hs.insertMember(host);
//        result.put("msg", "ok");
//        return result;
//    }

    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody Host host) {
        System.out.println(host.toString());

        Member member = new Member();

        Map<String, Object> result = new HashMap<>();
        PasswordEncoder pe = cc.passwordEncoder();
        member.setUserid(host.getHostid());
        member.setPwd( pe.encode(host.getPwd()) );
        member.setNickname(host.getNickname());
        member.setRole("host");

        int mseq = ms.insertMember(member);
        host.setMseq(mseq);

        try {
            host.setPwd(pe.encode(host.getPwd())); // 비밀번호 암호화
            hs.insertMember(host); // 회원가입 처리

            result.put("msg", "ok"); // 회원가입 성공 메시지
        } catch (Exception e) {
            result.put("error", "회원가입 중 오류가 발생했습니다."); // 오류 발생 시 메시지
            e.printStackTrace(); // 디버깅을 위한 예외 로그
        }
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

//    @PostMapping("/Update")
//    public HashMap<String, Object> Update(@RequestBody Host host){
//
//        HashMap<String, Object> result = new HashMap<>();
//
//        PasswordEncoder pe = cc.passwordEncoder();
//        host.setPwd( pe.encode(host.getPwd()) );
//
//        hs.update( host );
////        HttpSession session = request.getSession();
////        session.setAttribute("loginUser", host );
//
//        result.put("msg", "ok");
//        result.put("loginUser", host);
//
//        return result;
//    }


    @PostMapping("/update")
    public ResponseEntity<HashMap<String, Object>> Update(@RequestBody Host updateHost) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            Host host = hs.getHost(updateHost.getHostid());
            host.setNickname(updateHost.getNickname());
            host.setEmail(updateHost.getEmail());
            host.setPhone(updateHost.getPhone());
            if (host.getMember() != null && host.getMember().getUserid() == null) {
                ms.insertMember(host.getMember());
            }

//            ms
            hs.update(host);

            result.put("msg", "ok");
            result.put("loginUser", host);

            return ResponseEntity.ok(result);
        } catch (DataIntegrityViolationException e) {
            result.put("msg", "error");
            result.put("error", "데이터 무결성 위반: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } catch (Exception e) {
            result.put("msg", "error");
            result.put("error", "서버 오류: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
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
            response.sendRedirect("http://localhost:3001/getsnshostinfo/"+member.get().getUserid()+"/kakao");
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
    public void loginnaver(@RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) {
        try {
            OAuthToken oAuthToken = usersls.getNaverToken(code, state, naverClinet_id, naverRedirect_uri , "XxT4MYfubz");
            if (oAuthToken == null || oAuthToken.getAccess_token() == null) {
                throw new RuntimeException("Failed to obtain access token from Naver.");
            }

            // Access Token을 사용하여 사용자 정보 요청
            NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());
            if (naverapi == null || naverapi.getResponse() == null) {
                throw new RuntimeException("Failed to fetch user info from Naver.");
            }

            Optional<Member> member = usersls.getNaverHost(naverapi);
            if (!member.isPresent()) {
                throw new RuntimeException("Failed to get member information.");
            }

            response.sendRedirect("http://localhost:3001/getsnshostinfo/" + member.get().getUserid() + "/naver");
        } catch (Exception e) {
            e.printStackTrace(); // 로그에 오류 기록
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                response.getWriter().write("An error occurred: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }


    private String googleClinet_id="325806549360-rfnkgftjf45c468fs66jrf24k1o1ga8t.apps.googleusercontent.com";
    private String gooleRedirect_uri ="http://localhost:8070/host/sns/googlelogin";
    private String googleClientPw= "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";
    @RequestMapping("/sns/googlestart")
    public @ResponseBody String getgooleLogin(){
        String a = "<script  type='text/javascript'>" +
                "location.href='https://accounts.google.com/o/oauth2/v2/auth?"
                +"client_id="+googleClinet_id+
                "&redirect_uri="+gooleRedirect_uri+
                "&response_type=code" +
                "&scope=email profile"
                +"';" +
                "</script>";
        return a;
    }

    @RequestMapping("/sns/googlelogin")
    public void logingoole(@RequestParam("code") String code ,HttpServletResponse response) {
        System.out.println(code);
        OAuthToken oAuthToken = usersls.getGoogleToken(code,googleClinet_id, googleClientPw,gooleRedirect_uri);
        GoogleApi googleapi = null;
        try {
            googleapi = usersls.getGoogleProfile(oAuthToken.getAccess_token());
            if(googleapi == null) throw new SnsException("구글 로그인 실패");
            Optional<Member> member = usersls.getGoogleHost(googleapi);
            response.sendRedirect("http://localhost:3000/getsnshostinfo/"+member.get().getUserid()+"/google");

        } catch (SnsException | IOException e) {
            e.printStackTrace();
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteHost(@RequestParam String hostid) {
        Map<String, String> result = new HashMap<>();

        try {
            hs.deleteHost(hostid); // 호스트와 관련된 멤버 삭제 및 호스트 삭제 수행
            result.put("msg", "ok");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("msg", "error");
//            result.put("error", "회원 탈퇴 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
