package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import com.himedia.rentmon_back.entity.Space;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @Id
    private int rseq;
    private String content;
    private int rate;
    private String reply;
    private Timestamp replydate;
    private int sseq;
    private String userid;
    private Timestamp created_at;

    // ReviewImage 조회
    private List<ReviewImage> images;

    public static ReviewDTO fromEntity(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setRseq(review.getRseq());
        dto.setContent(review.getContent());
        dto.setCreated_at(review.getCreated_at());
        dto.setReply(review.getReply());
        dto.setReplydate(review.getReplydate());
        dto.setRate(review.getRate());
        dto.setSseq(review.getSpace().getSseq());
        dto.setUserid(review.getUser().getUserid());

        // Review에 연결된 ReviewImage 리스트를 DTO에 설정
        dto.setImages(review.getImages());

        return dto;
    }

    public static Review toEntity(ReviewDTO dto) {
        Review review = new Review();
        review.setRseq(dto.getRseq());
        review.setContent(dto.getContent());
        review.setCreated_at(dto.getCreated_at());
        review.setReply(dto.getReply());
        review.setReplydate(dto.getReplydate());
        review.setRate(dto.getRate());

        return review;
    }
}
