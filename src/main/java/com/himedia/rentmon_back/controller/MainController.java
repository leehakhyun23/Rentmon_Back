package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.dto.SpaceAndReviewRaterDTO;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainController {
    private final MainService ms;

    @PostMapping("/getRecommandSpace")
    public ResponseEntity<List<SpaceAndReviewRaterDTO>> getRecommandSpace(@RequestBody(required = false) User user) {
        List<SpaceAndReviewRaterDTO> spaceList =  ms.getRecommandSpace(user);
        return ResponseEntity.ok(spaceList);
    }

    @GetMapping("/getSapceList")
    public ResponseEntity<List<SpaceAndReviewRaterDTO>> getSapceList(@RequestParam("cnum") int cnum){
        List<SpaceAndReviewRaterDTO> list = ms.getCategorySpaceList(cnum);
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/getspaceviewlist")
    public ResponseEntity<List<Space>> getspaceviewlist (@RequestBody List<Integer> rctvw){
        List<Space> list = ms.getspaceviewlist(rctvw);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/searchPwd")
    public String searchPwd(@RequestParam("userid") String userid , @RequestParam("email") String email , @RequestParam("resetPasswordUrl") String resetPasswordUrl){
        User user = ms.searchUser(userid, email);
        if( user == null){
            return "회원가입된 계정이 없습니다.";
        }
        ms.pwdSearchMailsender( userid , email , resetPasswordUrl);

        return "메일을 보냈습니다. 해당 이메일에서 확인 해주세요.";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody Map<String, String> map){
        return ms.changePassword(map.get("password"), map.get("userid"));
    }

}
