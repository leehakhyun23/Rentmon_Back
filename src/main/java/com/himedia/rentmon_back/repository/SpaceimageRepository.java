package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.SpaceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SpaceimageRepository extends JpaRepository<SpaceImage, Long> {
    List<SpaceImage> findBySpace(Space space);
    List<SpaceImage> findBySpace_sseq(int sseq);
}
