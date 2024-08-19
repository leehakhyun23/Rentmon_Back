package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

    Optional<Hashtag> findByWord(String word);
}