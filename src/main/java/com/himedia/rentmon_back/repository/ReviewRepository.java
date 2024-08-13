package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    ArrayList findBySseq(int sseq);
}
