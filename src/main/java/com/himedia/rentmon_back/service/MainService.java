package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.SpaceRepository;
import com.himedia.rentmon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class MainService {
    private final SpaceRepository sp;
    public List<Space> getRecommandSpace(User user) {
        Pageable pageable = PageRequest.of(0,6);
        Page<Space> list = null;
        if(user != null && user.getStation() != null){
            list = sp.getRecommandSpace( user.getStation().substring(0,1) , user.getCategory1() , user.getCategory2() , user.getCategory3() ,pageable);
        }else{
            list = sp.getSpaceRecent(pageable);
        }
        return list.getContent();
    }
}
