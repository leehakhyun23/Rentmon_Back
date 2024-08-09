package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private final ReviewRepository rvr;



    public List<SpaceDTO> getSpaceList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Space> pageResult = sr.findAll(pageable);
        List<SpaceDTO> spaces = new ArrayList<>();

        for ( Space space : pageResult.getContent()){
            SpaceDTO spaceDTO = new SpaceDTO();

            // space에 담긴 정보 조회
            int sseq = space.getSseq();
            spaceDTO.setSseq(sseq);
            spaceDTO.setTitle(space.getTitle());
            spaceDTO.setContent(space.getContent());
            spaceDTO.setPrice(space.getPrice());
            spaceDTO.setHostid(space.getHostid());
            spaceDTO.setCnum(space.getCnum());
            spaceDTO.setProvince(space.getProvince());
            spaceDTO.setTown(space.getTown());
            spaceDTO.setVillage(space.getVillage());
            spaceDTO.setCreated_at(space.getCreated_at());


            // 찜수 조회, 리뷰 수 조회는 member가 완성되면 작성

            // spaceimages 조회
            ArrayList a = sir.findBySseq( sseq );
            spaceDTO.setImages(a);

            // hashspace 조회
            ArrayList b = hsr.findBySseq( sseq );
            spaceDTO.setHashtags(b);

            // reviews 조회
            ArrayList c= rvr.findBySseq( sseq );
            spaceDTO.setReviews(c);

            // List에 추가
            spaces.add(spaceDTO);
        }
        return spaces;
    }

    public Reservation findByUserid(String userid) {
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지, 1개 항목
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeHoursLater = now.plus(3, ChronoUnit.DAYS);

        Page<Reservation> rs = rr.findReservationsWithinNext3Hours(userid, now, threeHoursLater, pageable);
        if(rs !=null && rs.hasContent())return rs.getContent().get(0);
        else return null;
    }

    public SpaceDTO getSpace(int sseq) {
        SpaceDTO result = new SpaceDTO();

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

        // spaceimages 조회
        ArrayList a = sir.findBySseq( sseq );
        result.setImages(a);

        // hashspace 조회
        ArrayList b = hsr.findBySseq( sseq );
        result.setHashtags(b);

        // reviews 조회
        ArrayList c= rvr.findBySseq( sseq );
        result.setReviews(c);

        return result;

    }

}
