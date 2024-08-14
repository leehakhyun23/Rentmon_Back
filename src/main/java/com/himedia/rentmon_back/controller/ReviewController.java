package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.dto.SpaceDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
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
    public ResponseEntity<Review> InsertReview(@RequestPart("review") Review review, @RequestPart(value = "images", required = false) List<MultipartFile> images){
        if (images != null && !images.isEmpty()) {
            List<ReviewImage> reviewImages = new ArrayList<>();
            for (MultipartFile file : images) {
                ReviewImage image = new ReviewImage();
                image.setOriginname(file.getOriginalFilename());
                image.setRealname(file.getOriginalFilename());
                reviewImages.add(image);
            }
            review.setImages(reviewImages);
        }

        Review savedReview = reviewService.InsertReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/GetReviews/{sseq}")
    public ResponseEntity<List<ReviewDTO>> GetReviews(@PathVariable("sseq") int sseq){
        try{
            List<ReviewDTO> reviews = reviewService.getReviewList(sseq);
            return ResponseEntity.ok(reviews);
        }catch(Exception e){
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
