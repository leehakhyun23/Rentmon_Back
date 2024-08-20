package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.HashSpace;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashSpaceRepository extends JpaRepository<HashSpace, Long> {
    @Query("select h from HashSpace h where h.sseq.sseq = :sseq")
    List<HashSpace> getHashSpaceTag(int sseq);

}
