package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
}
