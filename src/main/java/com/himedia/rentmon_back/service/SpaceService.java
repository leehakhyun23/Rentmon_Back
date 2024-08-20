package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.FnumDTO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final SpaceimageRepository spaceimageRepository;
    private final ReservationRepository rr;
    private final ReviewRepository rvr;
    private final HostRepository hr;
    private final CategoryRepository cr;
    private final FacilityRepository fr;
    private final SpaceFacilityRepository sfr;
    private final HashtagRepository htr;
    private final HashSpaceRepository hhsr;
    private final SpaceimageRepository sir;

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
        Page<Reservation> rs = rr.findReservationsWithinNext3Hours(userid,now, threedaysLater, pageable);
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
        int sseq = savedSpace.getSseq();
        // 포스트의 content 추출
        String subtitle = space.getSubtitle();
        Matcher m = Pattern.compile("#([0-9a-zA-Z가-힣]*)").matcher(subtitle);
        Set<String> tags = new HashSet<>();
        while (m.find()) {
            tags.add(m.group(1));
        }

        for (String word : tags) {
            Optional<Hashtag> rec = htr.findByWord(word);
            Hashtag hashtag;
            if (!rec.isPresent()) {
                Hashtag hdnew = new Hashtag();
                hdnew.setWord(word);
                hashtag = htr.save(hdnew); // 새 Hashtag 저장
            } else {
                hashtag = rec.get(); // 기존 Hashtag 가져오기
            }

            // 새로운 HashSpace 엔티티 생성 및 저장
            HashSpace hs = new HashSpace();
            hs.setSseq(savedSpace); // Space 객체 설정
            hs.setHseq(hashtag); // Hashtag 객체 설정
            hhsr.save(hs);
        }
        for (String word : tags) {

        }
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

    public void insertfnum(FnumDTO request) {
        Integer sseq = request.getSseq();
        String[] numbers = request.getNumbers();

        if (sseq == null || numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Invalid data: sseq or numbers are missing or invalid");
        }

        // String[]를 Integer[]로 변환
        Integer[] numberInts = new Integer[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            try {
                numberInts[i] = Integer.parseInt(numbers[i]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format: " + numbers[i]);
            }
        }

        Space space = spaceRepository.findById(sseq)
                .orElseThrow(() -> new IllegalArgumentException("Space with sseq " + sseq + " not found"));

        for (Integer number : numberInts) {
            Facility facility = fr.findById(number)
                    .orElseThrow(() -> new IllegalArgumentException("Facility with fnum " + number + " not found"));

            SpaceFacility spaceFacility = new SpaceFacility();
            spaceFacility.setSpace(space);
            spaceFacility.setFacility(facility);

            // 올바른 레포지토리 사용
            sfr.save(spaceFacility);
        }
    }


    public void saveImageInfo(Integer sseq, List<String> originalnames, List<String> realnames) {
        // Space 엔티티를 sseq로 조회
        Space space = spaceRepository.findById(sseq).orElseThrow(() -> new RuntimeException("Space not found"));
        // 새로운 이미지 정보 저장
        for (int i = 0; i < originalnames.size(); i++) {
            SpaceImage image = new SpaceImage();
            image.setSpace(space);
            image.setOrigiName(originalnames.get(i));
            image.setRealName(realnames.get(i));
            image.setCreated_at(new Timestamp(System.currentTimeMillis()));

            spaceimageRepository.save(image);
        }
    }

}
