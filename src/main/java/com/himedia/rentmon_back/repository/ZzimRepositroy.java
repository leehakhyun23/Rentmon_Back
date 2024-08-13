package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Zzim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface ZzimRepositroy extends JpaRepository<Zzim, Integer> {

    List<Zzim> findByUserid(String userid);
}
