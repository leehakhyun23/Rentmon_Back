package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
public class SpaceDTO {
    private Space space;
    private List<Inquiry> inquiry;
    private List<Review> review;
    private List<Hashtag> hashtag;
    private int reviewCount;
    private int rating;
    private int zzimCount;

    public SpaceDTO(Space space, int allReivewCount, int allReivewRateCount , int zzimCount , List<Hashtag> hashtag) {
        this.space = space;
        this.reviewCount = allReivewCount;
        this.rating = allReivewRateCount;
        this.zzimCount = zzimCount;
        this.hashtag = hashtag;
    }

    public SpaceDTO(Space space, List<Inquiry> inquiry, List<Review> review, List<Hashtag> hashtag, int reviewCount, int rating, int zzimCount) {
        this.space = space;
        this.inquiry = inquiry;
        this.review = review;
        this.hashtag = hashtag;
        this.reviewCount = reviewCount;
        this.rating = rating;
        this.zzimCount = zzimCount;

    }

}
