package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.Hashtag;
import com.himedia.rentmon_back.entity.Space;
import lombok.Data;

import java.util.List;

@Data
public class SpaceAndReviewRaterDTO {

    private Space space;
    private int reviewCount;
    private int rating;
    private int zzimCount;
    private List<Hashtag> hashtag;

    public SpaceAndReviewRaterDTO(Space space, int allReivewCount, int allReivewRateCount , int zzimCount , List<Hashtag> hashtag) {
        this.space = space;
        this.reviewCount = allReivewCount;
        this.rating = allReivewRateCount;
        this.zzimCount = zzimCount;
        this.hashtag = hashtag;
    }
}
