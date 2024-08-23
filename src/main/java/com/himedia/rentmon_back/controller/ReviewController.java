package com.himedia.rentmon_back.controller;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import com.himedia.rentmon_back.service.ReviewService;
import com.himedia.rentmon_back.service.SpaceService;
import com.himedia.rentmon_back.util.PagingMj;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final SpaceService spaceService;
    private final ReviewService reviewService;

    @Autowired
    ServletContext context;


    @PostMapping("/InsertReview")
    public ResponseEntity<Review> InsertReview(@RequestPart("review") Review review, @RequestPart(value = "images", required = false) List<MultipartFile> images){
        if (images != null && !images.isEmpty()) {
            List<ReviewImage> reviewImages = new ArrayList<>();
            String path = context.getRealPath("/review_images");

            File directory = new File(path);
            if(!directory.exists()){
                directory.mkdirs();
            }

            for (MultipartFile file : images) {
                try {
                    // Generate a unique filename
                    Calendar today = Calendar.getInstance();
                    long dt = today.getTimeInMillis();
                    String filename = file.getOriginalFilename();
                    String fn1 = filename.substring(0, filename.indexOf("."));
                    String fn2 = filename.substring(filename.indexOf("."));
                    String uploadPath = path + File.separator + fn1 + dt + fn2;

                    // Save the file
                    file.transferTo(new File(uploadPath));

                    // Create ReviewImage object and set properties
                    ReviewImage image = new ReviewImage();
                    image.setOriginname(file.getOriginalFilename());
                    image.setRealname(fn1 + dt + fn2);
                    reviewImages.add(image);

                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                    // Optionally handle the error and provide feedback
                }
            }
            review.setImages(reviewImages);
        }

        Review savedReview = reviewService.InsertReview(review);
        return ResponseEntity.ok(savedReview);
    }

    @GetMapping("/GetReviews/{sseq}")
    public ResponseEntity<List<Review>> GetReviews(@PathVariable("sseq") int sseq){
        try{
            List<Review> reviews = reviewService.getReviewList(sseq);
            return ResponseEntity.ok(reviews);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/GetReviewList/{sseq}")
    public ResponseEntity<Map<String, Object>> GetReviewList(@PathVariable("sseq") int sseq, @RequestParam("page") int page){
        Map<String, Object> map = new HashMap<>();
        PagingMj paging = new PagingMj();
        paging.setCurrentPage(page);
        paging.setRecordAllcount(reviewService.getReviewALlCount(sseq));
        List<Review> list = reviewService.getReviewListwithPage(sseq,paging);
        map.put("list", list);
        map.put("paging", paging);
        return ResponseEntity.ok(map);
    }
}
