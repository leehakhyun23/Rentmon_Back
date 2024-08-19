package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.*;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.SpaceImage;
import com.himedia.rentmon_back.entity.User;
import com.himedia.rentmon_back.repository.*;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    private final HostRepository hr;
    private final CategoryRepository cr;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<Space> getSpaceList(int page, int size) {
        // 페이징 작업
        Pageable pageable = PageRequest.of(page, size);
        Page<Space> pageResult = spaceRepository.findAll(pageable);

        return pageResult.getContent();
    }

    public Reservation findByUserid(String userid) {
        Pageable pageable = PageRequest.of(0, 1); // 첫 페이지, 1개 항목
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threedaysLater = now.plus(3, ChronoUnit.DAYS);
        System.out.println("userid----------------"+userid);
        Page<Reservation> rs = rr.findReservationsWithinNext3Hours(userid,now, threedaysLater, pageable);
        System.out.println("userid----------------2" +rs.getContent());
        if(rs !=null && rs.hasContent())return rs.getContent().get(0);
        else return null;
    }

    public int insertSpace(Map<String, String> paramSpace) {
        Space space = new Space();
        Host host = hr.findById(paramSpace.get("hostid"))
                .orElseThrow(() -> new RuntimeException("Host not found"));
        Category category = cr.findById(Integer.parseInt(paramSpace.get("cnum"))).orElse(null);
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
        space.setHost(host); // Host 설정
        space.setCategory(category); // Category 설정
        space.setStarttime(Integer.parseInt(paramSpace.get("starttime")));
        space.setEndtime(Integer.parseInt(paramSpace.get("endtime")));
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

    public Space getSpace(int sseq) {
        Optional<Space> space = spaceRepository.findById(sseq);
        if (space.isPresent()) {
            return space.get();
        }
        else{
            return null;
        }
    }

}
