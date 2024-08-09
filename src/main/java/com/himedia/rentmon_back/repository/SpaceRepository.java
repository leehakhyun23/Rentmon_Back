package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer> {

}
