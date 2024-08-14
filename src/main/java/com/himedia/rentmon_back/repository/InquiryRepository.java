package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    @Query("SELECT COUNT(i) from Inquiry i where i.user.userid = :userid")
    Integer findByUseridCount(String userid);

}
