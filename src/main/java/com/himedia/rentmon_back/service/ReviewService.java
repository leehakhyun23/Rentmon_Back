package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Review;
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

    public Review InsertReview(Review review) {
        //Review Entity 내용 추출 후 review 저장
        Review savedReview = reviewRepository.save(review);

        // 리뷰와 관련된 이미지를 저장
        if (review.getImages() != null) {
            review.getImages().forEach(image -> {
                image.setReview(savedReview);
                reviewImageRepository.save(image);
            });
        }
        return savedReview;
    }

    public List<Review> getReviewList(int sseq) {
        // 특정 sseq 값으로 리뷰 리스트를 조회
        List<Review> reviews = reviewRepository.findBySpaceSseq(sseq);

        return reviews;
    }
}
