package com.himedia.rentmon_back.controller;

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
import com.himedia.rentmon_back.util.KakaoEception;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getuseinfo")
    public ResponseEntity<User> getUseInfo(@RequestParam("hostid") String hostid){
        User user = us.getUserInfo(hostid);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/gethostinfo")
    public ResponseEntity<Host> getHostInfo(@RequestParam("hostid") String hostid){
        Host host = hs.getHostInfo(hostid);
        return ResponseEntity.ok(host);
    }


    private String kakaoclinet_id="0a261c5101ce9fc314d111962604fbae";
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
            if(kakaoProfile == null) throw new KakaoEception("카카오 로그인 실패" , "oAutoToken도 같이 확인해주세요.");

            System.out.println(kakaoProfile);
            Optional<Member> member = usersls.getKakaoHost(kakaoProfile);
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
        OAuthToken oAuthToken = usersls.getNaverToken(code, state, naverClinet_id, naverRedirect_uri);

        NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());


    }
//
//    @Value("${kakao.client_id}")
//    private String client_id;
//    @Value("${kakao.redirect_uri}")
//    private String redirect_uri;
//
//    @RequestMapping("/kakaostart")
//    public @ResponseBody String kakaostart() {
//        String a = "<script type='text/javascript'>"
//                + "location.href='https://kauth.kakao.com/oauth/authorize?"
//                + "client_id=" + client_id + "&"
//                + "redirect_uri=" + redirect_uri + "&"
//                + "response_type=code';" + "</script>";
//        return a;
//    }

//
//    @RequestMapping("/kakaoLogin")
//    public void loginKakao( HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
//        String code = request.getParameter("code");
//        String endpoint = "https://kauth.kakao.com/oauth/token";
//        URL url = new URL(endpoint);
//        String bodyData = "grant_type=authorization_code&";
//        bodyData += "client_id=ce26567e0e05c2813336f21fc8d6b806&";
//        bodyData += "redirect_uri=http://localhost:8070/host/kakaoLogin&";
//        bodyData += "code=" + code;
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//        conn.setDoOutput(true);
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
//        bw.write(bodyData);
//        bw.flush();
//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//        String input = "";
//        StringBuilder sb = new StringBuilder();
//        while ((input = br.readLine()) != null) {
//            sb.append(input);
//        }
//
//        Gson gson = new Gson();
//        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
//        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
//        URL url2 = new URL(endpoint2);
//
//        HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
//        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
//        conn2.setDoOutput(true);
//        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
//        String input2 = "";
//        StringBuilder sb2 = new StringBuilder();
//        while ((input2 = br2.readLine()) != null) {
//            sb2.append(input2);
//            System.out.println(input2);
//        }
//        Gson gson2 = new Gson();
//        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
//        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
//        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
//        System.out.println("id : " + kakaoProfile.getId());
//        System.out.println("KakaoAccount-Email : " + ac.getEmail());
//        System.out.println("Profile-Nickname : " + pf.getNickname());
//        Host host = hs.getHostBySnsid( kakaoProfile.getId() );
//        if( host == null) {
//            host = new Host();
//            //member.setEmail( pf.getNickname() );
//            host.setEmail( ac.getEmail() );  // 전송된 이메일이 없으면 pf.getNickname()
//            host.setNickname( pf.getNickname() );
//            host.setProvider( "kakao" );
//            PasswordEncoder pe = cc.passwordEncoder();  // 비밀번호 암호화 도구
//            host.setPwd(  pe.encode("kakao") );
//            host.setSnsid( kakaoProfile.getId() );
//            hs.insertHost(host);
//        }
//        //HttpSession session = request.getSession();
//        //session.setAttribute("loginUser", member);
//        String nick = URLEncoder.encode(pf.getNickname(),"UTF-8");
//        response.sendRedirect("http://localhost:8070/kakaosaveinfo/" + nick);
//    }

    @PostMapping("/emailcheck")
    public HashMap<String, Object> emailcheck( @RequestParam("email") String email ){
        HashMap<String, Object> result = new HashMap<String, Object>();
        Host host = hs.getHost( email );
        if( host != null ) result.put("msg", "no");
        else result.put("msg", "yes");
        return result;
    }

    @PostMapping("/nicknamecheck")
    public HashMap<String, Object> nicknamecheck( @RequestParam("nickname") String nickname){
        HashMap<String, Object> result = new HashMap<String, Object>();
        Host host = hs.getHostByNickname( nickname );
        if( host != null ) result.put("msg", "no");
        else result.put("msg", "yes");
        return result;
    }

    @GetMapping("/gethostinfo")
    public Host localLogin(@RequestParam("userid") String userid) {
        return hs.getHostLogin(userid);
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
//
//    @GetMapping("/getLoginHost")
//    public HashMap<String, Object> getLoginHost(HttpServletRequest request) {
//        HashMap<String, Object> result = new HashMap<>();
//        HttpSession session = request.getSession();
//        result.put("loginHost" , session.getAttribute("loginHost") );
//        return result;
//    }
//
//    @GetMapping("/logout")
//    public HashMap<String , Object> logout(HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        session.removeAttribute("loginHost");
//        return null;
//    }
//
//    @PostMapping("/idcheck")
//    public HashMap<String, Object> idcheck( @RequestParam("hostid") String hostid ) {
//        HashMap<String, Object> result = new HashMap<>();
//        System.out.println("hostid : " + hostid);
//        Host host1 = hs.getHost(hostid);
//        if (host1 == null) {
//            result.put("res", "1");
//        } else {
//            result.put("res", "0");
//        }
//        return result;
//    }
//
//    @PostMapping("/insertHost")
//    public HashMap<String, Object> insertMember( @RequestBody Host host ) {
//        hs.insertHost( host );
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("msg", "ok");
//        return result;
//    }
//
//    @PostMapping("/withDrawal")
//    public HashMap<String, Object> withDrawal( @RequestParam("hostid") String hostid ) {
//        HashMap<String, Object> result = new HashMap<>();
//        hs.withDrawal(hostid);
//        return result;
//    }

    }
