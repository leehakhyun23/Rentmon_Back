package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ChatInfoDto;
import com.himedia.rentmon_back.dto.UserDTO;
import com.himedia.rentmon_back.dto.usersnsdto.GoogleApi;
import com.himedia.rentmon_back.dto.usersnsdto.KakaoProfile;
import com.himedia.rentmon_back.dto.usersnsdto.NaverApi;
import com.himedia.rentmon_back.dto.usersnsdto.OAuthToken;
import com.himedia.rentmon_back.entity.Category;
import com.himedia.rentmon_back.entity.ChatMsg;
import com.himedia.rentmon_back.entity.Member;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.ChatMessageRepository;
import com.himedia.rentmon_back.service.*;
import com.himedia.rentmon_back.util.SnsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final MemberService ms;
    private final UserService us;
    private final CategoryService cs;
    private final UserSnsLoginService usersls;
    private final ChatService chatService;

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
            if(kakaoProfile == null) throw new SnsException("카카오 로그인 실패");
            System.out.println(kakaoProfile);
            Optional<Member> member = usersls.getKakaoMember(kakaoProfile);
            response.sendRedirect("http://localhost:3000/getsnsuserinfo/"+member.get().getUserid()+"/kakao");
        } catch (SnsException e) {
            e.printStackTrace();
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
    public void loginnaver(@RequestParam("code") String code,@RequestParam("state") String state ,HttpServletResponse response){
        OAuthToken oAuthToken = usersls.getNaverToken(code,state,naverClinet_id,naverRedirect_uri,"wNH8RZ5hit");
        try {
            NaverApi naverapi = usersls.getLoginAPI(oAuthToken.getAccess_token());
            if(naverapi == null) throw new SnsException("네이버 로그인 실패");
            Optional<Member> member = usersls.getNaverMember(naverapi);
            response.sendRedirect("http://localhost:3000/getsnsuserinfo/"+member.get().getUserid()+"/naver");
        } catch (SnsException | IOException e) {
            throw new RuntimeException(e);
        } 
    }


    private String googleClinet_id="1006482080940-2h22le2b0elv4lv4hivccbeao91et93u.apps.googleusercontent.com";
    private String gooleRedirect_uri ="http://localhost:8070/user/sns/googlelogin";
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
            Optional<Member> member = usersls.getGoogleMember(googleapi);
            response.sendRedirect("http://localhost:3000/getsnsuserinfo/"+member.get().getUserid()+"/google");

        } catch (SnsException | IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/join/idcheck")
    public ResponseEntity<Boolean> idCheck(@RequestParam("userid")String userid){
        Boolean idCheck = usersls.isUesrTrue(userid);
        return ResponseEntity.ok(idCheck);
    }

    @PostMapping("/join/mailsend")
    public ResponseEntity<String> mailSend (@RequestParam("email") String email){
        String code = usersls.mailSender(email);
        return  ResponseEntity.ok(code);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join (@ModelAttribute UserDTO userDTO , @RequestParam(value = "profileimg" , required = false) MultipartFile profileimg){

        usersls.joinUser(userDTO, profileimg);
        return ResponseEntity.ok(userDTO.getUserid());
    }


    @PostMapping("/join/categoryset")
    public ResponseEntity<String> joincategoryset(@RequestBody Map<String, Object> data ){
        List<Integer> category = (List<Integer>) data.get("category");
        String station = (String) data.get("station");
        String userid = (String) data.get("userid");

        usersls.insertInterest(category, station,userid);
        return ResponseEntity.ok("ok");
    }

    @RequestMapping("/menucountarray")
    public ResponseEntity<Map<String , Integer>> menucountarray(@RequestParam ("userid") String userid){
        Map<String , Integer> list = us.getMenuCount(userid);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/getCategoryList")
    public ResponseEntity<List<Category>> getCategoryList(){
        List<Category> list = cs.getCategoryList();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/changeProfile")
    public ResponseEntity<String> changeProfile(@RequestParam(value = "profileimg" , required = false) MultipartFile profileimg , @RequestParam("userid") String userid ){
        System.out.println("profileimg profileimg -==="+profileimg);
        us.setProfileimg(userid, profileimg);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/updatename")
    public ResponseEntity<String> updatename(@RequestParam("userid")String userid, @RequestParam("name") String name){
        us.updatename(userid, name);
        return ResponseEntity.ok(userid+" : " + name);
    }
    @PostMapping("/updatephone")
    public ResponseEntity<String> updatephone(@RequestParam("userid")String userid, @RequestParam("phone") String phone){
        us.updatephone(userid, phone);
        return ResponseEntity.ok(userid+" : " + phone);
    }
    @PostMapping("/updatepwd")
    public ResponseEntity<String> updatepwd(@RequestParam("userid")String userid, @RequestParam("password") String password){
        ms.updatepassword(userid, password);
        return ResponseEntity.ok(userid+" : " + password);
    }

    @GetMapping("/getCrseq")
    public ChatInfoDto getCrseqAndUnreadMessages(@RequestParam("userid") String userid) {
        return chatService.getChatInfo(userid);
    }
}
