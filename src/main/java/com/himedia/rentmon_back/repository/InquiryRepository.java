package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Inquiry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

    int countByUserUserid(String userid);

    @Query("SELECT i from Inquiry i where i.user.userid = :userid order by i.iseq desc ")
    Page<Inquiry> getInqueryList(String userid, Pageable pageable);



    @Query("select i from Inquiry i where i.space.sseq = :sseq order by i.iseq desc ")
    Page<Inquiry> getInqueryHostList(String sseq, Pageable pageable);


    int countBySpaceSseq(Integer sseq);

    List<Inquiry> findBySpaceSseq(Integer sseq);


    Page<Inquiry> findBySpaceSseq(Integer sseq, Pageable pageable);

    @Query("SELECT i FROM Inquiry i WHERE i.space.sseq IN :sseqs")
    List<Inquiry> findBySseqIn(List<Integer> sseqs);
}
