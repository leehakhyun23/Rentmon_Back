package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.MypageUsedResevationDTO;
import com.himedia.rentmon_back.dto.SpaceAndReviewRaterDTO;
import com.himedia.rentmon_back.entity.HashSpace;
import com.himedia.rentmon_back.entity.Hashtag;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class MainService {
    private final SpaceRepository sp;
    private final ReviewRepository rr;
    private final ZzimRepositroy zr;
    private final HashSpaceRepository hsr;
    private final HashtagRepository hr;
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


}
