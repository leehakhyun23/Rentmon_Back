package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Spaceimage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SpaceimageRepository extends JpaRepository<Spaceimage, Long> {

    ArrayList findBySseq(int sseq);
}
