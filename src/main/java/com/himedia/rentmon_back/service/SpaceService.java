package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.repository.ReservationRepository;
import com.himedia.rentmon_back.repository.HashSearchRepository;
import com.himedia.rentmon_back.repository.SpaceRepository;
import com.himedia.rentmon_back.repository.SpaceimageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository sr;
    private final SpaceimageRepository sir;
    private final ReservationRepository rr;
    private final HashSearchRepository hsr;



    public List<SpaceDTO.SpaceList> getSpaceList() {
        List<Space>onlySpaceList = sr.findAll( Sort.by(Sort.Direction.DESC, "sseq"));
        List<SpaceDTO.SpaceList> list = new ArrayList<>();
        SpaceDTO spaceDTO = new SpaceDTO();



        for ( Space onlyspace : onlySpaceList){
            SpaceDTO.SpaceList space = spaceDTO.new SpaceList();

            // space에 담긴 정보 조회
            int sseq = onlyspace.getSseq();
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
            space.setSpaceimages(a);

            // hashspace 조회
            ArrayList b = hsr.findBySseq( sseq );
            space.setSpaceHashTags(b);

            // List에 추가
            list.add(space);
        }
        return list;
    }

    public Reservation findByUserid(String userid) {
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지, 1개 항목
        List<Reservation> reservations = rr.findLatestByUserid(userid, pageable);
        System.out.println( reservations.get(0));
        return reservations.get(0);

    }

    public SpaceDTO.SpaceList getSpace(int sseq) {
        SpaceDTO spaceDTO = new SpaceDTO();
        SpaceDTO.SpaceList result = spaceDTO.new SpaceList();

        // Space에 담긴 정보 조회
        Optional<Space> onlyspace = sr.findBySseq(sseq);
        if (onlyspace.isPresent()) {
            result.setSseq(sseq);
            result.setTitle(onlyspace.get().getTitle());
            result.setContent(onlyspace.get().getContent());
            result.setPrice(onlyspace.get().getPrice());
            result.setHostid(onlyspace.get().getHostid());
            result.setCnum(onlyspace.get().getCnum());
            result.setProvince(onlyspace.get().getProvince());
            result.setTown(onlyspace.get().getTown());
            result.setVillage(onlyspace.get().getVillage());
            result.setCreated_at(onlyspace.get().getCreated_at());
        }
        else {
            return null;
        }

        // 이미지 조회
        ArrayList a = sir.findBySseq( sseq );
        result.setSpaceimages(a);

        // 해시태그 조회
        ArrayList b = hsr.findBySseq( sseq );
        result.setSpaceHashTags(b);

        return result;

    }

}
