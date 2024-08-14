package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Review;
import com.himedia.rentmon_back.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findBySpaceSseq(int sseq);

//    ArrayList findBySseq(int sseq);
}
