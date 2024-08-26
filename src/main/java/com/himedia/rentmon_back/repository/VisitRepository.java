package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    @Query("SELECT v FROM Visit v WHERE v.createdAt >= :startDate AND v.createdAt <= :endDate")
    List<Visit> findVisitsByPeriod();
}
