package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceAndReviewRaterDTO;
import com.himedia.rentmon_back.entity.*;
import com.himedia.rentmon_back.repository.*;
import com.himedia.rentmon_back.util.MailSend;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class MainService {
    private final UserRepository ur;
    private final SpaceRepository sp;
    private final ReviewRepository rr;
    private final ZzimRepositroy zr;
    private final HashSpaceRepository hsr;
    private final HashtagRepository hr;
    private final MailSend ms;
    private final MemberRepository msr;

    public List<SpaceAndReviewRaterDTO> getRecommandSpace(User user) {
        Pageable pageable = PageRequest.of(0,6);
        List<SpaceAndReviewRaterDTO> result = spaceReviewAndHashTagGet(pageable, user);

        return result;
    }

    public List<SpaceAndReviewRaterDTO> getCategorySpaceList(int cnum) {
        List<SpaceAndReviewRaterDTO> result = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,6);
        Page<Space> list = null;
        if(cnum == 0 ){
            list = sp.getSpaceRecent( pageable);
        }else{
            list = sp.getCategoryList( pageable , cnum );
        }
        for(Space space : list){
            List<HashSpace> HashSpaceTags = hsr.getHashSpaceTag(space.getSseq());
            List<Hashtag> hashList = new ArrayList<>();
            for(HashSpace hashSpace : HashSpaceTags){
                hashList.add(hr.getHashTag(hashSpace.getHseq().getHseq()));
            }
            result.add(new SpaceAndReviewRaterDTO(space, rr.getAllReivewCount(space.getSseq()),rr.getAllReivewRateCount(space.getSseq()), zr.getAllZzimCount(space.getSseq()) , hashList));
        }
        return result;
    }


    //공간 리뷰 해시태그 모두 가져오기
    public  List<SpaceAndReviewRaterDTO> spaceReviewAndHashTagGet(  Pageable pageable ,User user){
        List<SpaceAndReviewRaterDTO> result = new ArrayList<>();
        Page<Space> list = null;
        if(user != null && user.getStation() != null){
            list = sp.getRecommandSpace( user.getStation().substring(0,1) , user.getCategory1() , user.getCategory2() , user.getCategory3() ,pageable);
        }else{
            list = sp.getSpaceRecent(pageable);
        }

        for(Space space : list){
            List<HashSpace> HashSpaceTags = hsr.getHashSpaceTag(space.getSseq());
            List<Hashtag> hashList = new ArrayList<>();
            for(HashSpace hashSpace : HashSpaceTags){
                hashList.add(hr.getHashTag(hashSpace.getHseq().getHseq()));
            }
            result.add(new SpaceAndReviewRaterDTO(space, rr.getAllReivewCount(space.getSseq()),rr.getAllReivewRateCount(space.getSseq()), zr.getAllZzimCount(space.getSseq()) , hashList));
        }

        return result;
    }


    public List<Space> getspaceviewlist(List<Integer> rctvw) {
        List<Space> list = new ArrayList<>();
        for(int rcv : rctvw){
            list.add(sp.findBySseq(rcv).get());
        }
        return  list;
    }


    public User searchUser(String userid, String email) {
        Optional<User> user = ur.findByUseridAndEmail(userid, email);
        if(user.isPresent()) return user.get();
        else return null;
    }

    public void pwdSearchMailsender(String userid, String email , String resetPasswordUrl) {
        String content = "<div style='line-height: 1.6; padding: 20px; max-width: 600px; margin: auto;'>" +
                "<p style='font-size: 24px; font-weight: bold; margin-bottom: 20px; color: #333;'>안녕하세요,</p>" +
                "<p style='font-size: 16px; color: #555;'>로그인 사이트를 방문해 주셔서 감사합니다.</p>" +
                "<p style='font-size: 16px; color: #555;'>비밀번호 재설정을 위해 아래 링크를 클릭해주세요:</p>" +
                "<p style='text-align: left;'>" +
                "<a href='" + resetPasswordUrl + "' target='_blank' style='padding: 18px 20px; " +
                " font-size: 16px; color: #fff; background: #0090df; text-decoration: none;  font-weight: bold; display: inline-block;\n" +
                " margin: 10px 0;'>비밀번호 재설정 페이지로 이동</a></p>" +
                "<p style='font-size: 16px; color: #555;'>감사합니다.</p>" +
                "<p style='font-size: 16px; color: #555;'>로그인 사이트 팀</p>" +
                "</div>";
        ms.sendEmail(email, resetPasswordUrl+"/"+userid , content);
    }

    public String changePassword(String password, String userid) {
        PasswordEncoder ps = new BCryptPasswordEncoder();
        Optional<Member> memberOptional  = msr.findByUseridAndRole(userid , "user");
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            member.setPwd(ps.encode(password));
            msr.save(member);
            return  "ok";
        }
        return  "서버 에러!!";
    }
}
