package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Integer>, JpaSpecificationExecutor<Declaration> {
    // admin
    int countByUserAndSpaceIsNull(User user);
    int countBySpaceAndHostIsNull(Space space);
}
