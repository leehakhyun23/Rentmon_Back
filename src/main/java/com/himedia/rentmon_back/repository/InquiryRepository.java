package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {
}
