package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.FnumDTO;
import com.himedia.rentmon_back.dto.SpaceAndReviewRaterDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.dto.SpaceImageDTO;
import com.himedia.rentmon_back.dto.SpaceUpdateRequest;
import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.service.SpaceService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/getSpaceList")
    public ResponseEntity<List<SpaceDTO>> getSpaceList(
            @RequestParam int page,
            @RequestParam(required = false) int cnum,
            @RequestParam(required = false) String searchword,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String reservestart,
            @RequestParam(required = false) String reserveend,
            @RequestParam(required = false) int sortOption
            ) {

        try {
            int size = 6;  // 페이지당 표시할 공간의 수
            List<SpaceDTO> spaceList = spaceService.getSpaceList(page, size, cnum, searchword, province, reservestart, reserveend, sortOption);
            return ResponseEntity.ok(spaceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/getreserve")
    public Reservation getreserve(@RequestParam("userid") String userid) {
        return spaceService.findByUserid(userid);

    }


    @GetMapping("/getSpace/{sseq}")
    public ResponseEntity<SpaceDTO> getSpace(@PathVariable("sseq") int sseq) {
        try{
            SpaceDTO spaceDTO = spaceService.getSpace(sseq);
            return ResponseEntity.ok(spaceDTO);
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/insertSpace")
    public ResponseEntity<Integer> insertSpace(@RequestBody Map<String, String> space) {
        System.out.println(space.toString());
        int sseq = spaceService.insertSpace(space);
        return ResponseEntity.ok(sseq);
    }


    @PostMapping("/insertfnum")
    public ResponseEntity<String> insertfnum(@RequestBody FnumDTO request) {
    Integer sseq = request.getSseq();
    String[] numbers = request.getNumbers();

    if (sseq == null || numbers == null || numbers.length == 0) {
        return ResponseEntity.badRequest().body("Invalid data: sseq or numbers are missing or invalid");
    }
    try {
        // sseq와 numbers 출력
        spaceService.insertfnum(request);
        return null;
    } catch (Exception e) {
        // 예외 발생 시 에러 메시지 반환
        return ResponseEntity.status(500).body("Error processing data");
        }
    }

    @Autowired
    private ServletContext context;

    @PostMapping("/insertImgSrc")
    public ResponseEntity<String> insertImgSrc(@RequestBody SpaceImageDTO request) {
        try {
            Integer sseq = request.getSseq();
            List<String> originalnames = request.getOriginalnames();
            List<String> realnames = request.getRealnames();

            // 이미지 정보를 저장하는 서비스 메서드 호출
            spaceService.saveImageInfo(sseq, originalnames, realnames);

            return ResponseEntity.ok("Image information saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving image information.");
        }
    }



    @PostMapping("/imgup")
    public ResponseEntity<Map<String, String>> fileup(@RequestParam("image") MultipartFile file) {
        Map<String, String> result = new HashMap<>();
        try {
            // 이미지 저장 경로 설정
            String path = context.getRealPath("/space_images");
            Calendar today = Calendar.getInstance();
            long dt = today.getTimeInMillis();
            String filename = file.getOriginalFilename();
            String fn1 = filename.substring(0, filename.indexOf("."));
            String fn2 = filename.substring(filename.indexOf("."));
            String realname = fn1 + dt + fn2;  // 실 저장 파일명
            String uploadPath = path + "/" + realname;

            // 파일 저장
            file.transferTo(new File(uploadPath));

            // 원본 파일명과 저장된 파일명을 결과로 반
            result.put("originalname", filename);
            result.put("realname", realname);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/spacename")
    public ResponseEntity<List<String>> getTitlesByHostid(@RequestParam("hostid") String hostid) {
        try {
            List<String> titles = spaceService.findTitlesByHostid(hostid);
            return ResponseEntity.ok(titles);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/spaces")
    public ResponseEntity<List<Space>> getSpacesByHostid(@RequestParam("hostid") String hostid) {
        try {
            List<Space> spaces = spaceService.spaceList(hostid);

            if (spaces.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            System.out.println(spaces);
            return ResponseEntity.ok(spaces);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/colspace")
    public ResponseEntity<Space> hakgetSpace(@RequestParam("sseq") int sseq) {
        try {
            Space space = spaceService.hakgetSpace(sseq);
            return ResponseEntity.ok(space);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/collectspace/{sseq}")
    public ResponseEntity<?> updateSpace(@PathVariable int sseq, @RequestBody SpaceUpdateRequest request) {
        try {
            Space updatedSpace = spaceService.updateSpace(sseq, request);
            return ResponseEntity.ok(updatedSpace);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/updateFacilities")
    public ResponseEntity<String> updateFacilities(@RequestBody FnumDTO dto) {
        spaceService.updateFacilities(dto);
        return ResponseEntity.ok("Facilities updated successfully");
    }

    @DeleteMapping("/delete/{sseq}")
    public ResponseEntity<Void> deleteSpace(@PathVariable int sseq) {
        boolean isDeleted = spaceService.deleteSpace(sseq);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/spacetime/{sseq}")
    public ResponseEntity<String> updateSpace(
            @PathVariable Integer sseq,
            @RequestBody SpaceUpdateRequest updateRequest) {
        try {
            spaceService.updateTime(sseq, updateRequest.getStarttime(), updateRequest.getEndtime());
            return new ResponseEntity<>("Space updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getSpaceTitle")
    public ResponseEntity<String> getSpaceTitle(@RequestParam Integer sseq) {
        try {
            String title = spaceService.getSpaceTitle(sseq);
            System.out.println(ResponseEntity.ok(title));
            return ResponseEntity.ok(title);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
