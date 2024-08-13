package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    Collection<Object> findByUserid(String userid);
}
