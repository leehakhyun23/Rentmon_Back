package com.himedia.rentmon_back.service;

import com.himedia.rentmon_back.dto.ReviewDTO;
import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import com.himedia.rentmon_back.repository.ReviewImageRepository;
import com.himedia.rentmon_back.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
//            reviewImage.setRseq(rseq); // Review 엔티티와 연관 설정

            reviewImageRepository.save(reviewImage);
        }

    }

    public List<Review> getReviewList() {
//        List<ReviewDTO> reviews = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findAll();

//        for ( Review review : reviewList){
//            ReviewDTO reviewDTO = new ReviewDTO();
//
//            // Review에 담긴 정보 조회
//            reviewDTO.setRseq(review.getRseq());
//            reviewDTO.setContent(review.getContent());
//            reviewDTO.setRate(review.getRate());
//            reviewDTO.setReply(review.getReply());
//
//            // ReviewImage 조회
//            ArrayList<ReviewImage> a = reviewImageRepository.findByRseq( reviewDTO.getRseq() );
//            reviewDTO.setImages(a);
//
//            //List에 추가
////            reviews.add(reviewDTO);
//        }

        return reviewList;
    }
}
