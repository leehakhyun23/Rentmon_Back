package com.himedia.rentmon_back.controller;


import com.google.gson.Gson;
import com.himedia.rentmon_back.dto.KakaoProfile;
import com.himedia.rentmon_back.dto.OAuthToken;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.security.CustomSecurityConfig;
import com.himedia.rentmon_back.service.HostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("/host")
public class HostController {

    @Autowired
    HostService hs;

    @Value("${kakao.client_id}")
    private String client_id;
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @RequestMapping("/kakaostart")
    public @ResponseBody String kakaostart() {
        String a = "<script type='text/javascript'>"
                + "location.href='https://kauth.kakao.com/oauth/authorize?"
                + "client_id=" + client_id + "&"
                + "redirect_uri=" + redirect_uri + "&"
                + "response_type=code';" + "</script>";
        return a;
    }
    @Autowired
    CustomSecurityConfig cc;

    @RequestMapping("/kakaoLogin")
    public void loginKakao( HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        String code = request.getParameter("code");
        String endpoint = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(endpoint);
        String bodyData = "grant_type=authorization_code&";
        bodyData += "client_id=ce26567e0e05c2813336f21fc8d6b806&";
        bodyData += "redirect_uri=http://localhost:8070/host/kakaoLogin&";
        bodyData += "code=" + code;

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setDoOutput(true);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        bw.write(bodyData);
        bw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String input = "";
        StringBuilder sb = new StringBuilder();
        while ((input = br.readLine()) != null) {
            sb.append(input);
        }

        Gson gson = new Gson();
        OAuthToken oAuthToken = gson.fromJson(sb.toString(), OAuthToken.class);
        String endpoint2 = "https://kapi.kakao.com/v2/user/me";
        URL url2 = new URL(endpoint2);

        HttpsURLConnection conn2 = (HttpsURLConnection) url2.openConnection();
        conn2.setRequestProperty("Authorization", "Bearer " + oAuthToken.getAccess_token());
        conn2.setDoOutput(true);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
        String input2 = "";
        StringBuilder sb2 = new StringBuilder();
        while ((input2 = br2.readLine()) != null) {
            sb2.append(input2);
            System.out.println(input2);
        }
        Gson gson2 = new Gson();
        KakaoProfile kakaoProfile = gson2.fromJson(sb2.toString(), KakaoProfile.class);
        KakaoProfile.KakaoAccount ac = kakaoProfile.getAccount();
        KakaoProfile.KakaoAccount.Profile pf = ac.getProfile();
        System.out.println("id : " + kakaoProfile.getId());
        System.out.println("KakaoAccount-Email : " + ac.getEmail());
        System.out.println("Profile-Nickname : " + pf.getNickname());
        Host host = hs.getHostBySnsid( kakaoProfile.getId() );
        if( host == null) {
            host = new Host();
            //member.setEmail( pf.getNickname() );
            host.setEmail( ac.getEmail() );  // 전송된 이메일이 없으면 pf.getNickname()
            host.setNickname( pf.getNickname() );
            host.setProvider( "kakao" );
            PasswordEncoder pe = cc.passwordEncoder();  // 비밀번호 암호화 도구
            host.setPwd(  pe.encode("kakao") );
            host.setSnsid( kakaoProfile.getId() );
            hs.insertHost(host);
        }
        //HttpSession session = request.getSession();
        //session.setAttribute("loginUser", member);
        String nick = URLEncoder.encode(pf.getNickname(),"UTF-8");
        response.sendRedirect("http://localhost:8070/kakaosaveinfo/" + nick);
    }

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

    @PostMapping("/join")
    public HashMap<String, Object> join( @RequestBody Host host){
        HashMap<String, Object> result = new HashMap<String, Object>();
        PasswordEncoder pe = cc.passwordEncoder();
        host.setPwd( pe.encode(host.getPwd()) );
        hs.insertHost(host);
        result.put("msg", "ok");
        return result;
    }


//    @PostMapping("/locallogin")
//    public HashMap<String , Object> localLogin(@RequestBody Host host, HttpServletRequest request ) {
//        HashMap<String , Object> result = new HashMap<>();
//        Host host1 = hs.getHost( host.getHostid() );
//        if(host1 == null ) {
//            result.put("msg", "해당 아이디가 없습니다");
//        }else if( !host1.getPwd().equals( host.getPwd() ) ) {
//            result.put("msg", "패스워드가 틀립니다.");
//        }
////        else if( host1.get().equals("N")){
////            result.put("msg", "현재 아이디가 비활성화되어있습니다. 관리자에 아이디 재가입 및 활성화를 문의하세요");
////        }
//        else {
//            HttpSession session = request.getSession();
//            session.setAttribute("loginHost", host1);
//            result.put("msg", "ok");
//        }
//        return result;
//    }
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
