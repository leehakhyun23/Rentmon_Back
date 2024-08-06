package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {

}
