package com.himedia.rentmon_back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Grade;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.HostRepository;
import com.himedia.rentmon_back.repository.MemberRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserSnsLoginService {
    private final MemberRepository mr;
    private final HostRepository hr;
    private final UserRepository ur;
    private final PasswordEncoder pe;
    public OAuthToken getKakaoToken(String code, String kakaoclinetId, String redirectUri) {
        OAuthToken token = new OAuthToken();
        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code").append("&client_id=")
                    .append(kakaoclinetId).append("&redirect_uri=").append(redirectUri)
                    .append("&code=").append(code);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sbkey = new StringBuilder();
            String input = "";
            while ((input = br.readLine()) != null) {
                sbkey.append(input);
                System.out.println(input);
            }
            ObjectMapper mapper = new ObjectMapper();
            token = mapper.readValue(sbkey.toString(), OAuthToken.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(token));

        } catch (IOException e) {
            throw new RuntimeException("failed to get Kakao OAuth token", e);
        }

        return token;
    }


    public KakaoProfile getKakaoProfile(OAuthToken oAuthToken) {
        KakaoProfile kakaoProfile =null;
        try {
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+oAuthToken.getAccess_token());
            String line = "";
            BufferedReader br =new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            kakaoProfile = mapper.readValue(sb.toString(), KakaoProfile.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(kakaoProfile));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return kakaoProfile;
    }

    public Optional<Member> getKakaoMember(KakaoProfile kakaoProfile) {
        Optional<Member> member = mr.findByUseridAndRole(String.valueOf(kakaoProfile.getId()), "user");
        if(member.isEmpty()){
            Member joinkakaoMember = new Member();
            joinkakaoMember.setUserid(String.valueOf(kakaoProfile.getId()));
            joinkakaoMember.setRole("user");
            joinkakaoMember.setPwd(pe.encode("kakao"));
            mr.save(joinkakaoMember);

            User user = new User();
            user.setUserid(String.valueOf(kakaoProfile.getId()));
            user.setMseq(joinkakaoMember.getMseq());
            user.setPwd(joinkakaoMember.getPwd());
            user.setName(kakaoProfile.getProperties().getNickname());
            user.setGnum(new Grade(1, "bronze", 0));
            ur.save(user);
            member = Optional.of(joinkakaoMember);
        }

        return member;
    }

    public Optional<Member> getKakaoHost(KakaoProfile kakaoProfile) {
        Optional<Member> member = mr.findByUseridAndRole(String.valueOf(kakaoProfile.getId()), "host");
        if(member.isEmpty()){
            Member joinkakaoMember = new Member();
            joinkakaoMember.setUserid(String.valueOf(kakaoProfile.getId()));
            joinkakaoMember.setRole("host");
            joinkakaoMember.setPwd(pe.encode("kakao"));
            mr.save(joinkakaoMember);

            Host host = new Host();
            host.setHostid(String.valueOf(kakaoProfile.getId()));
            host.setMseq(new Member(joinkakaoMember.getMseq(), "", "", "", null));
            host.setPwd(joinkakaoMember.getPwd());
            host.setName(kakaoProfile.getProperties().getNickname());
            hr.save(host);
            member = Optional.of(joinkakaoMember);
        }

        return member;
    }

    public OAuthToken getNaverToken(String code, String state, String naverClinetId, String naverRedirectUri) {
        OAuthToken oAuthToken = null;
        try {
            URL url= new URL("https://nid.naver.com/oauth2.0/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code").append("&client_id=")
                    .append(naverClinetId).append("&client_secret=wNH8RZ5hit")
                    .append("&redirect_uri=").append(naverRedirectUri).append("&code=").append(code)
                    .append("&state=").append(state);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            bw.write(sb.toString());
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sbkey = new StringBuilder();
            String input = "";
            while ((input = br.readLine()) != null) {
                sbkey.append(input);
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            oAuthToken = mapper.readValue(sbkey.toString(), OAuthToken.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(oAuthToken));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return oAuthToken;
    }

    public NaverApi getLoginAPI(String accessToken) throws MalformedURLException {
        NaverApi naverApi =null;
        URL url = new URL("https://openapi.naver.com/v1/nid/me");
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization","Bearer "+accessToken);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            ObjectMapper mapper = new ObjectMapper();
            naverApi = mapper.readValue(sb.toString(), NaverApi.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(naverApi));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return naverApi;
    }
}
