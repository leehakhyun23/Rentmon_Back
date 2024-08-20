package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {
    Optional<Hashtag> findByWord(String word);
    
    @Query("select h from Hashtag  h where h.hseq = :hseq")
    Hashtag getHashTag(int hseq);

    
}


