package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import com.himedia.rentmon_back.repository.ReviewImageRepository;
import com.himedia.rentmon_back.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    public void InsertReview(Review review, List<String> images) {
        Review savedReview = reviewRepository.save(review);
        int rseq = savedReview.getRseq();

        // Images를 추출해서 reviewImage에 save
        for (String imageName : images) {
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setRealname(imageName);
            reviewImage.setReview(savedReview); // Review 엔티티와 연관 설정

            reviewImageRepository.save(reviewImage);
        }

    }
}
