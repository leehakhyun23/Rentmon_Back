package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import lombok.Data;

import java.util.List;

@Data
public class SpaceAndReviewDTO {
    private Space space;
    private List<Review> reviewList;
}
