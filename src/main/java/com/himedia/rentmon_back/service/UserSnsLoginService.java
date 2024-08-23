package com.himedia.rentmon_back.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.dto.usersnsdto.GoogleApi;
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
import com.himedia.rentmon_back.util.ImageFileupload;
import com.himedia.rentmon_back.util.MailSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
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
    private final MailSend ms;
    private final ImageFileupload imgupload;

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
                System.out.println(line);
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
            joinkakaoMember.setNickname(kakaoProfile.getProperties().getNickname());
            mr.save(joinkakaoMember);

            int mseq = mr.findByUseridOne(joinkakaoMember.getUserid());

            User user = new User(new Member());
            user.setUserid(String.valueOf(kakaoProfile.getId()));
            user.setMseq(mseq);
            user.setProvider("kakao");
            user.setIslogin(true);
            user.setSnsid(joinkakaoMember.getUserid());
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
            joinkakaoMember.setNickname(kakaoProfile.getProperties().getNickname());
            joinkakaoMember = mr.save(joinkakaoMember);


            Host host = new Host();
            host.setHostid(String.valueOf(kakaoProfile.getId()));
            host.setMseq(joinkakaoMember.getMseq());
            host.setPwd(joinkakaoMember.getPwd());
            host.setNickname(kakaoProfile.getProperties().getNickname());
            host.setProvider("kakao");

            hr.save(host);
            member = Optional.of(joinkakaoMember);
        }

        return member;
    }

    public OAuthToken getNaverToken(String code, String state, String naverClinetId, String naverRedirectUri , String secreatkey) {
        System.out.println("Received code: " + code);
        System.out.println("Received state: " + state);
        System.out.println("Received naverClinetId: " + naverClinetId);
        System.out.println("Received naverRedirectUri: " + naverRedirectUri);
        OAuthToken oAuthToken = null;
        try {
            System.out.println("kljsflkjdslkfjdlks");
            URL url= new URL("https://nid.naver.com/oauth2.0/token");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code").append("&client_id=")
                    .append(naverClinetId).append("&client_secret=").append(secreatkey)
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

    public Optional<Member> getNaverMember(NaverApi naverapi) {
        Optional<Member> member = mr.findByUseridAndRole(String.valueOf(naverapi.getResponse().getId()), "user");
        if(member.isEmpty()){
            Member joinNaverMember = new Member();
            joinNaverMember.setUserid(String.valueOf(naverapi.getResponse().getId()));
            joinNaverMember.setRole("user");
            joinNaverMember.setPwd(pe.encode("naver"));
            mr.save(joinNaverMember);

            User user = new User();
            user.setUserid(String.valueOf(joinNaverMember.getUserid()));
            user.setMseq(joinNaverMember.getMseq());
            user.setName(naverapi.getResponse().getName());
            user.setSnsid(joinNaverMember.getUserid());
            user.setIslogin(true);
            user.setProvider("naver");
            user.setEmail(naverapi.getResponse().getEmail());
            user.setGnum(new Grade(1, "bronze", 0));
            ur.save(user);
            member = Optional.of(joinNaverMember);
        }

        return member;
    }

    public Optional<Member> getNaverHost(NaverApi naverapi) {
        Optional<Member> member = mr.findByUseridAndRole(String.valueOf(naverapi.getResponse().getId()), "host");
        if(member.isEmpty()){
            Member joinNaverMember = new Member();
            joinNaverMember.setUserid(String.valueOf(naverapi.getResponse().getId()));
            joinNaverMember.setRole("host");
            joinNaverMember.setPwd(pe.encode("naver"));
            mr.save(joinNaverMember);

            Host host = new Host();
            host.setHostid(String.valueOf(joinNaverMember.getUserid()));
            host.setMseq(joinNaverMember.getMseq());
            host.setPwd(joinNaverMember.getPwd());
            host.setNickname(naverapi.getResponse().getNickname());
            host.setProvider("naver");
            hr.save(host);
            member = Optional.of(joinNaverMember);
        }

        return member;
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


    public OAuthToken getGoogleToken(String code, String googleClientId, String googleClientPw, String googleRedirectUri) {
        OAuthToken oAuthToken = null;

        try {
            URL url = new URL("https://oauth2.googleapis.com/token");
            HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            StringBuilder sb = new StringBuilder();

            sb.append("code=").append(URLEncoder.encode(code, "UTF-8"))
                    .append("&client_id=").append(URLEncoder.encode(googleClientId, "UTF-8"))
                    .append("&client_secret=").append(URLEncoder.encode(googleClientPw, "UTF-8"))
                    .append("&redirect_uri=").append(URLEncoder.encode(googleRedirectUri, "UTF-8"))
                    .append("&grant_type=authorization_code");

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

    public GoogleApi getGoogleProfile(String accessToken) throws MalformedURLException {
        GoogleApi googleApi = null;
        URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo");
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
                System.out.println(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            googleApi = mapper.readValue(sb.toString(), GoogleApi.class);
            System.out.println(googleApi);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return googleApi;
    }

    public Optional<Member> getGoogleMember(GoogleApi googleapi) {
        Optional<Member> member = mr.findByUseridAndRole(googleapi.getId(), "user");
        if(member.isEmpty()){
            Member joinGoogleMember = new Member();
            joinGoogleMember.setUserid(googleapi.getId());
            joinGoogleMember.setRole("user");
            joinGoogleMember.setPwd(pe.encode("google"));
            mr.save(joinGoogleMember);

            User user = new User();
            user.setUserid(joinGoogleMember.getUserid());
            user.setMseq(joinGoogleMember.getMseq());
            user.setName(googleapi.getName());
            user.setSnsid(joinGoogleMember.getUserid());
            user.setIslogin(true);
            user.setProvider("google");
            user.setEmail(googleapi.getEmail());
            user.setGnum(new Grade(1, "bronze", 0));
            ur.save(user);
            member = Optional.of(joinGoogleMember);
        }

        return member;
    }

    public Optional<Member> getGoogleHost(GoogleApi googleapi) {
        Optional<Member> member = mr.findByUseridAndRole(googleapi.getId(), "host");
        if(member.isEmpty()){
            Member joinGoogleMember = new Member();
            joinGoogleMember.setUserid(googleapi.getId());
            joinGoogleMember.setRole("host");
            joinGoogleMember.setPwd(pe.encode("google"));
            mr.save(joinGoogleMember);

            Host host = new Host();
            host.setHostid(joinGoogleMember.getUserid());
            host.setMseq(joinGoogleMember.getMseq());

            host.setPwd(joinGoogleMember.getPwd());
            host.setNickname(googleapi.getName());
            host.setProvider("google");
            hr.save(host);
            member = Optional.of(joinGoogleMember);
        }

        return member;
    }

    public Boolean isUesrTrue(String userid) {
        User user = ur.findByUserid(userid);
        if(user != null) return true;
        else return false;
    }

    public Boolean isHostTrue(String hostid) {
        Host host = hr.findByHostid(hostid);
        if(host != null) return true;
        else return false;
    }

    public String mailSender(String email) {
       return ms.setEmail(email);
    }


    public void joinUser(UserDTO userDTO, MultipartFile profileimg) {
        Member member  = new Member();
        member.setUserid(userDTO.getUserid());
        member.setRole("user");
        member.setPwd(pe.encode(userDTO.getPassword()));
        member = mr.save(member);

        System.out.println("서비스 profileimg----------------------------------------------------------"+profileimg);
        User user = new User();
        user.setUserid(member.getUserid());
        user.setMseq(member.getMseq());
        user.setPhone(userDTO.getPhone());
        user.setIslogin(true);
        user.setGnum(new Grade(1, "bronze", 0));
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        if(profileimg !=null) user.setProfileimg(imgupload.saveFile(profileimg , "/profile_images"));
        ur.save(user);
    }


    public void insertInterest(List<Integer> category, String station, String userid) {
        User user = ur.findByUserid(userid);
        user.setCategory1(category.get(0));
        user.setCategory2(category.get(1));
        user.setCategory3(category.get(2));
        user.setStation(station);
        ur.save(user);
    }
}
