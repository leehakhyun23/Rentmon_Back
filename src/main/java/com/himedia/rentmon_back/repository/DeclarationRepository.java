package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclarationRepository extends JpaRepository<Declaration, Integer> {
//    @Query("SELECT new com.himedia.rentmon_back.dto.DeclarationDTO(d.dseq, d.reply, m.userid, s.title, d.created_at) " +
//            "FROM Declaration d " +
//            "INNER JOIN Member m ON d.writer = m.mseq " +
//            "INNER JOIN Space s ON d.sseq = s.sseq ")
//    List<DeclarationDTO> findAllByDeclaration();
}
