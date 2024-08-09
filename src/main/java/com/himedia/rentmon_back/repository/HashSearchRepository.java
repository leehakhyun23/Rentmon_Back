package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.HashSearch;
import com.himedia.rentmon_back.entity.HashSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface HashSearchRepository extends JpaRepository<HashSearch, Long> {
    ArrayList findBySseq(int sseq);
}
