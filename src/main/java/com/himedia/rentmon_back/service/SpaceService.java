package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.SpaceImage;
import com.himedia.rentmon_back.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final SpaceimageRepository spaceimageRepository;
    private final ReservationRepository rr;
    private final HashSearchRepository hsr;
    private final ReviewRepository rvr;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<SpaceDTO> getSpaceList(int page, int size) {
        // 페이징 작업
        Pageable pageable = PageRequest.of(page, size);
        Page<Space> pageResult = spaceRepository.findAll(pageable);

        return pageResult.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private SpaceDTO convertToDTO(Space space) {
        SpaceDTO dto = new SpaceDTO();

        //Space DTO에 삽입
        dto = dto.fromEntity(space);

        //SpaceImage 조회해서 삽입
        List<String> imageNames = spaceimageRepository.findBySpace(space)
                .stream()
                .map(SpaceImage::getRealName)
                .collect(Collectors.toList());
        dto.setImageNames(imageNames);

        return dto;

    }

    public Reservation findByUserid(String userid) {
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지, 1개 항목
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeHoursLater = now.plus(3, ChronoUnit.DAYS);

        Page<Reservation> rs = rr.findReservationsWithinNext3Hours(userid,now, threeHoursLater, pageable);
        System.out.println(rs.getContent());
        if(rs !=null && rs.hasContent())return rs.getContent().get(0);
        else return null;
    }

    public int insertSpace(Map<String, String> paramSpace) {
        Space space = new Space();
//        space.setCnum(Integer.parseInt(paramSpace.get("cnum")));
        space.setTitle(paramSpace.get("title"));
        space.setSubtitle(paramSpace.get("subtitle"));
        space.setPrice(Integer.parseInt(paramSpace.get("price")));
        space.setMaxpersonnal(Integer.parseInt(paramSpace.get("maxpersonnal")));
        space.setContent(paramSpace.get("content"));
        space.setCaution(paramSpace.get("caution"));
        space.setZipcode(paramSpace.get("zipcode"));
        space.setProvince(paramSpace.get("province"));
        space.setTown(paramSpace.get("town"));
        space.setVillage(paramSpace.get("village"));
        space.setAddressdetail(paramSpace.get("address_detail"));
//        space.setHostid(paramSpace.get("hostid"));
//        try {
//            // Convert starttime and endtime from String to Timestamp
//            space.setStarttime((paramSpace.get("starttime")));
//            space.setEndtime(convertStringToTimestamp(paramSpace.get("endtime")));
//        } catch (Exception e) {
//            e.printStackTrace(); // Print the stack trace for debugging
//        }

        // Save the Space entity
        Space savedSpace = spaceRepository.save(space);
        return savedSpace.getSseq();
    }

    private Timestamp convertStringToTimestamp(String dateStr) throws Exception {
        if (dateStr == null || dateStr.isEmpty()) {
            throw new IllegalArgumentException("Date string is null or empty"); // Better error handling
        }
        try {
            // Parse the date string to ZonedDateTime
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
            // Convert ZonedDateTime to Instant
            Instant instant = zonedDateTime.toInstant();
            // Convert Instant to Timestamp
            return Timestamp.from(instant);
        } catch (Exception e) {
            // Provide more information about what went wrong
            throw new IllegalArgumentException("Failed to parse date string: " + dateStr, e);
        }
    }

    public SpaceDTO getSpace(int sseq) {
        Optional<Space> space = spaceRepository.findById(sseq);
        //Space 엔티티 조회
        SpaceDTO dto = convertToDTO(space.get());

        //SpaceImage 엔티티 리스트 조회
        List<String> imageNames = spaceimageRepository.findBySpace(space.get())
                .stream()
                .map(SpaceImage::getRealName)
                .collect(Collectors.toList());
        dto.setImageNames(imageNames);

        return dto;
    }
}
