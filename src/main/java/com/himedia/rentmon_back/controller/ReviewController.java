package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.service.ReviewService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {


    private final ReviewService rs;

    @PostMapping("/InsertReview")
    public ResponseEntity<Review> InsertReview(@RequestBody Map<String ,Object> data){
        String userid = (String) data.get("userid");
        int rate = (Integer) data.get("rate");
        String content = (String) data.get("content");
        String created_at_str = (String) data.get("created_at");

        // 문자열을 Timestamp로 변환하는 과정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  // 문자열 포맷에 맞게 조정
        Timestamp created_at = null;
        try {
            Date date = sdf.parse(created_at_str);
            created_at = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();  // 에러 로그
            // 적절한 에러 처리를 추가
        }


        List<String> images = (List<String>) data.get("images");

        Review review = new Review();
        review.setUserid(userid);
        review.setCreated_at(created_at);
        review.setRate(rate);
        review.setContent(content);

        rs.InsertReview(review, images);

       return ResponseEntity.ok(new Review());
    }

    @Autowired
    ServletContext context;

    @PostMapping("/imgup")
    public HashMap<String, Object> imgup(
            @RequestParam("image") MultipartFile file ){
        HashMap<String, Object> result = new HashMap<String, Object>();
        String path = context.getRealPath("/review_images");
        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = file.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf(".") );
        String fn2 = filename.substring(filename.indexOf(".") );
        String uploadPath = path + "/" + fn1 + dt + fn2;
        try {
            file.transferTo( new File(uploadPath) );
            result.put("reviewimage", fn1 + dt + fn2);
        } catch (IllegalStateException | IOException e) {e.printStackTrace();}
        return result;
    }

}
