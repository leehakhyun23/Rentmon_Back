package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.repository.SpaceRepository;
import com.himedia.rentmon_back.repository.SpaceimageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository sr;
    private final SpaceimageRepository sir;



    public List<SpaceDTO.SpaceList> getSpaceList() {
        List<Space>onlySpaceList = sr.findAll( Sort.by(Sort.Direction.DESC, "sseq"));
        List<SpaceDTO.SpaceList> list = new ArrayList<>();


        for ( Space onlyspace : onlySpaceList){
            SpaceDTO.SpaceList space = null;
            space.setSseq(onlyspace.getSseq());
            space.setTitle(onlyspace.getTitle());
            space.setContent(onlyspace.getContent());
            space.setPrice(onlyspace.getPrice());
            space.setHostid(onlyspace.getHostid());
            space.setCnum(onlyspace.getCnum());
            space.setProvince(onlyspace.getProvince())
            ;
            space.setTown(onlyspace.getTown());
            space.setVillage(onlyspace.getVillage());
            space.setCreated_at(onlyspace.getCreated_at());


            // 찜수 조회, 리뷰 수 조회는 member가 완성되면 작성

            // spaceimages 조회

            // hashspace 조회

            list.add(space);
        }
        return list;
    }

}
