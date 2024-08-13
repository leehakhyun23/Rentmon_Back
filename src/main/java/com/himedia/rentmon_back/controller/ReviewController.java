package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
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
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final SpaceService spaceService;
    private final ReviewService reviewService;

    @PostMapping("/InsertReview")
    public ResponseEntity<Review> InsertReview(@RequestBody Map<String ,Object> data){
        String userid = (String) data.get("userid");
        int rate = (Integer) data.get("rate");
        String content = (String) data.get("content");
        int sseq = (Integer) data.get("sseq");
        List<String> images = (List<String>) data.get("images");



        Review review = new Review();
//        review.setUser(new User(user));
//        review.setUserid(userid);
//        review.setSseq(sseq);
//        review.setCreated_at(created_at);
        review.setRate(rate);
        review.setContent(content);

        reviewService.InsertReview(review, images);

       return ResponseEntity.ok(new Review());
    }

    @GetMapping("/getReviews")
    public ResponseEntity<List<Review>> getReview(){
        try{
            List<Review> reviews = reviewService.getReviewList();
            return ResponseEntity.ok(reviews);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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
