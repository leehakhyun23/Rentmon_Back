package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import com.himedia.rentmon_back.repository.ReviewImageRepository;
import com.himedia.rentmon_back.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    public Review InsertReview(Review review) {

        // Review 엔티티와 ReviewImage 간의 연관 관계 설정
        if (review.getImages() != null) {
            review.getImages().forEach(image -> image.setReview(review));
        }

        // Review 저장
        return reviewRepository.save(review);
    }

    public List<ReviewDTO> getReviewList(int sseq) {
        // 특정 sseq 값으로 리뷰 리스트를 조회
        List<Review> reviews = reviewRepository.findBySpaceSseq(sseq);

        // 각 Review를 ReviewDTO로 변환
        return reviews.stream().map(review -> {
            ReviewDTO dto = ReviewDTO.fromEntity(review);
            dto.setImages(review.getImages()); // Review에 연결된 ReviewImage 리스트를 설정
            return dto;
        }).collect(Collectors.toList());
    }
}
