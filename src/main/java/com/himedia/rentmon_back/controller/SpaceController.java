package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.service.ReviewService;
import com.himedia.rentmon_back.service.SpaceService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
public class SpaceController {

    private final SpaceService spaceService;

    @GetMapping("/getSpaceList/{page}")
    public ResponseEntity<List<SpaceDTO>> getSpaceList(@PathVariable int page) {
        try{
            int size = 6;
            List<SpaceDTO> spaceList = spaceService.getSpaceList(page, size);
            return ResponseEntity.ok(spaceList);
        } catch (Exception e){
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
            SpaceDTO space = spaceService.getSpace(sseq);
            return ResponseEntity.ok(space);
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

    @Autowired
    ServletContext context;

    @PostMapping("/imgup")
    public HashMap<String, Object> fileup(
            @RequestParam("image") MultipartFile file ){
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/space_image");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf(".") );
        String fn2 = filename.substring(filename.indexOf(".") );
        String uploadPath = path + "/" + fn1 + dt + fn2;
        try {
            file.transferTo( new File(uploadPath) );
            result.put("savefilename", fn1 + dt + fn2);
        } catch (IllegalStateException | IOException e) {e.printStackTrace();}
        return result;
    }


}
