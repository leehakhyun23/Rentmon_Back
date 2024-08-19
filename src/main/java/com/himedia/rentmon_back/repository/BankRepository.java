package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Integer> {
}
