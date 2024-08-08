package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.dto.AdminDTO;
import com.himedia.rentmon_back.dto.AdminHostDTO;
import com.himedia.rentmon_back.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostRepository extends JpaRepository<Host, String> {
    @Query(value = "SELECT h.hostid, h.name, c.name AS category, f.name AS fee, s.price, h.phone, s.province, s.town, s.village, s.addressdetail, COALESCE(d.declacount, 0) AS declaCount " +
            "FROM host h " +
            "INNER JOIN space s ON h.hostid = s.hostid " +
            "INNER JOIN category c ON c.cnum = s.cnum " +
            "INNER JOIN fee f ON f.fnum = s.fnum " +
            "LEFT JOIN (" +
            "    SELECT s1.sseq, COUNT(d1.sseq) AS declacount " +
            "    FROM space s1 " +
            "    LEFT JOIN declaration d1 ON s1.sseq = d1.sseq " +
            "    GROUP BY s1.sseq " +
            ") d ON s.sseq = d.sseq",
            nativeQuery = true)
    List<AdminHostDTO> findHostBySpaceJpql();
}
