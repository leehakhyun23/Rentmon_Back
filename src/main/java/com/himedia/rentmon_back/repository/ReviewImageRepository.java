package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
//    ArrayList<ReviewImage> findByRseq(int rseq);
}
