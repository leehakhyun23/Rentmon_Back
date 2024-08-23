package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.dto.FnumDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.dto.SpaceUpdateRequest;
import com.himedia.rentmon_back.entity.*;
import com.himedia.rentmon_back.repository.*;
import com.himedia.rentmon_back.specification.SpaceSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final ZzimRepositroy zzimRepositroy;
    private final InquiryRepository inquiryRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ReviewRepository reviewRepository;

//    public List<Space> getSpaceList(int page, int size) {
//        // 페이징 작업
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Space> pageResult = spaceRepository.findAll(pageable);
//
//        return pageResult.getContent();
//    }

    public List<SpaceDTO> getSpaceList(int page, int size, int cnum, String searchword, String province, String reservestart, String reserveend, int sortOption) {
        Specification<Space> spec = Specification.where(null);

        if (cnum > 0) {  // cnum이 0보다 클 때만 필터링 적용
            spec = spec.and(SpaceSpecifications.hasCnum(cnum));
        }

        if (searchword != null && !searchword.isEmpty()) {
            spec = spec.and(SpaceSpecifications.hasSearchword(searchword));
        }

        if (province != null && !province.isEmpty()) {
            spec = spec.and(SpaceSpecifications.hasProvince(province));
        }

        if (reservestart != null && !reservestart.isEmpty() && reserveend != null && !reserveend.isEmpty()) {
            spec = spec.and(SpaceSpecifications.isAvailableDuring(reservestart, reserveend));
        }

        Sort sort = getSort(sortOption);

        // Space 목록을 조회
        List<Space> spaces = spaceRepository.findAll(spec, PageRequest.of(page, size, sort)).getContent();


        // Space를 SpaceDTO로 변환
        List<SpaceDTO> spaceDTOs = spaces.stream().map(space -> {
            int reviewCount = rvr.getAllReivewCount(space.getSseq()); // 리뷰 개수 계산
            int rating = rvr.getAllReivewRateCount(space.getSseq()); // 평점 계산
            int zzimCount = zzimRepositroy.getAllZzimCount(space.getSseq()); // 찜 개수 계산
            List<Hashtag> hashtags = getHashtags(space); // 해시태그 목록 가져오기

            return new SpaceDTO(space, reviewCount, rating, zzimCount, hashtags);
        }).collect(Collectors.toList());

//        // DTO를 기준으로 추가 정렬
//        switch (sortOption) {
//            case 3: // 리뷰순
//                spaceDTOs.sort(Comparator.comparingInt(SpaceDTO::getReviewCount).reversed());
//                break;
//            case 4: // 찜순
//                spaceDTOs.sort(Comparator.comparingInt(SpaceDTO::getZzimCount).reversed());
//                break;
//            default:
//                // 기본 정렬을 처리하거나, 필요한 경우 예외 처리
//                break;
//        }

        return spaceDTOs;

    }

    private Sort getSort(int sortOption) {
        switch (sortOption) {
            case 0:
                return Sort.by(Sort.Order.desc("sseq"));
            case 1:
                return Sort.by(Sort.Order.asc("price"));
            case 2:
                return Sort.by(Sort.Order.desc("price"));
            case 3:
                return Sort.by(Sort.Order.desc("sseq"));
            case 4:
                return Sort.by(Sort.Order.desc("sseq"));
            default:
                return Sort.unsorted(); // Default no sorting
        }
    }


    private List<Hashtag> getHashtags(Space space) {
        // space에 대한 해시태그 목록을 반환하는 로직 작성
        return space.getHashtags().stream()
                .map(HashSpace::getHseq)
                .collect(Collectors.toList());
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
        space.setAddress(paramSpace.get("address"));
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

    public SpaceDTO getSpace(int sseq) {
        SpaceDTO spaceDTO = new SpaceDTO();
        //Space space, List<Inquiry> inquiry, List<Review> review, List<Hashtag> hashtag, int reviewCount, int rating, int zzimCount


        Space space = new Space();

        Optional<Space> getSpace = spaceRepository.findById(sseq);
        if (getSpace.isPresent()) {
            space = getSpace.get();
        } else {
            space = null;
        }

        List<Inquiry> inquiryList = inquiryRepository.findBySpaceSseq(sseq);

        List<Review> reviewList = reviewRepository.findBySpaceSseqOrderByRseqDesc(sseq);

        List<Hashtag> tagList = getHashtags(space); // 해시태그 목록 가져오기

        int reviewCount = rvr.getAllReivewCount(sseq); // 리뷰 개수 계산
        int rating = rvr.getAllReivewRateCount(sseq); // 평점 계산
        int zzimCount = zzimRepositroy.getAllZzimCount(sseq); // 찜 개수 계산

        spaceDTO = new SpaceDTO(space, inquiryList, reviewList, tagList, reviewCount, rating, zzimCount);

        return spaceDTO;
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

    public List<String> findTitlesByHostid(String hostid) {
        Host host = hr.findById(hostid)
                .orElseThrow(() -> new RuntimeException("Host not found"));

        return spaceRepository.findDistinctTitlesByHost(host);
    }

    public List<Space> spaceList(String hostid) {
        Host host = hr.findById(hostid)
                .orElseThrow(() -> new RuntimeException("Host not found"));

        return spaceRepository.findByHost(host);
    }

    public Space hakgetSpace(int sseq) {
        return (Space) spaceRepository.findBysseq(sseq)
                .orElseThrow(() -> new RuntimeException("Space not found with sseq: " + sseq));
    }

    public Space updateSpace(int sseq, SpaceUpdateRequest request) {
        Space space = spaceRepository.findById(sseq)
                .orElseThrow(() -> new RuntimeException("Space not found"));

        space.setTitle(request.getTitle());
        space.setSubtitle(request.getSubtitle());
        space.setPrice(request.getPrice());
        space.setMaxpersonnal(request.getMaxpersonnal());
        space.setContent(request.getContent());
        space.setCaution(request.getCaution());
        space.setZipcode(request.getZipcode());
        space.setProvince(request.getProvince());
        space.setTown(request.getTown());
        space.setVillage(request.getVillage());
        space.setAddressdetail(request.getAddressdetail());
        space.setAddress(request.getAddress());

        return spaceRepository.save(space);
    }

    public List<SpaceFacility> getFacilitiesBySpace(int sseq) {
        return sfr.findBySpace_Sseq(sseq);
    }

    public void updateFacilities(FnumDTO dto) {
        Integer sseq = dto.getSseq();
        String[] numbers = dto.getNumbers();
        System.out.println(sseq+"-------------------------------------------------------------");
        if (numbers == null) {
            throw new IllegalArgumentException("Facility numbers cannot be null");
        }

        // Clear existing facilities for the space
        sfr.deleteBySpace_Sseq(sseq);
        System.out.println(sseq);
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

    public boolean deleteSpace(int sseq) {

        if (spaceRepository.existsById(sseq)) {
            spaceRepository.deleteById(sseq);
            return true;
        }
        return false;
    }

    public void updateTime(int sseq, int starttime, int endtime) {
        Optional<Space> spaceOptional = spaceRepository.findById(sseq);
        if (spaceOptional.isPresent()) {
            Space space = spaceOptional.get();
            space.setStarttime(starttime);
            space.setEndtime(endtime);
            spaceRepository.save(space);
        } else {
            throw new RuntimeException("Space not found");
        }
    }

    // admin
    public List<AdminDTO.ResponseCategory> findAll() {
        return spaceRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        space -> space.getCategory().getName(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> AdminDTO.ResponseCategory.builder()
                        .name(entry.getKey())
                        .value(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
