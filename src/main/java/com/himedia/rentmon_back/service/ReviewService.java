package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.entity.Inquiry;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.repository.ReviewImageRepository;
import com.himedia.rentmon_back.repository.ReviewRepository;
import com.himedia.rentmon_back.util.PagingMj;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        List<Review> reviews = reviewRepository.findBySpaceSseqOrderByRseqDesc(sseq);

        return reviews;
    }

    public int getReviewALlCount(int sseq) {
        return reviewRepository.countBySpaceSseq(sseq);

    }

    public List<Review> getReviewListwithPage(int sseq, PagingMj paging) {
        Pageable pageable = PageRequest.of(paging.getCurrentPage()-1, paging.getRecordrow() , Sort.by("rseq").descending());
        Page<Review> list = reviewRepository.findBySpaceSseq(sseq, pageable);
        return list.getContent();
    }
}
