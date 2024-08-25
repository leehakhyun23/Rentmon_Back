package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Declaration;
import com.himedia.rentmon_back.entity.Space;
import com.himedia.rentmon_back.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Integer>, JpaSpecificationExecutor<Declaration> {
    // admin
    int countByUserAndSpaceIsNull(User user);
    int countBySpaceAndHostIsNull(Space space);
    @Query("SELECT d FROM Declaration d WHERE d.host IS NULL AND d.space IS NOT NULL")
    Page<Declaration> findUserSpaceDeclarations(Pageable pageable);
    @Query("SELECT d FROM Declaration d WHERE d.space IS NULL AND d.host IS NOT NULL")
    Page<Declaration> findHostUserDeclarations(Pageable pageable);
}
