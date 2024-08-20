package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.SpaceFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceFacilityRepository extends JpaRepository<SpaceFacility, Integer> {
}
