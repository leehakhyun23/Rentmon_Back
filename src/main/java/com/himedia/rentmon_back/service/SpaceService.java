package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.repository.HashSpaceRepository;
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
    private final HashSpaceRepository hsr;



    public List<SpaceDTO.SpaceList> getSpaceList() {
        List<Space>onlySpaceList = sr.findAll( Sort.by(Sort.Direction.DESC, "sseq"));
        List<SpaceDTO.SpaceList> list = new ArrayList<>();
        SpaceDTO spaceDTO = new SpaceDTO();


        for ( Space onlyspace : onlySpaceList){
            int sseq = onlyspace.getSseq();

            SpaceDTO.SpaceList space = spaceDTO.new SpaceList();
            space.setSseq(sseq);
            space.setTitle(onlyspace.getTitle());
            space.setContent(onlyspace.getContent());
            space.setPrice(onlyspace.getPrice());
            space.setHostid(onlyspace.getHostid());
            space.setCnum(onlyspace.getCnum());
            space.setProvince(onlyspace.getProvince());
            space.setTown(onlyspace.getTown());
            space.setVillage(onlyspace.getVillage());
            space.setCreated_at(onlyspace.getCreated_at());


            // 찜수 조회, 리뷰 수 조회는 member가 완성되면 작성

            // spaceimages 조회
            ArrayList a = sir.findBySseq( sseq );
            space.setSpaceImages(a);

            // hashspace 조회
            ArrayList b = hsr.findBySseq( sseq );
            space.setSpaceHashTags(b);

            // List에 추가
            list.add(space);
        }
        return list;
    }
}
