package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Reservation, Integer> {
//    ArrayList findBySseq(int sseq);
}
