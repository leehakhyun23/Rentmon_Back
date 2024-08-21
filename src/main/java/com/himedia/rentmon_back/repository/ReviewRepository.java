package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findBySpaceSseqOrderByRseqDesc(int sseq);

    @Query("select  coalesce(cast(avg(r.rate) as int), 0) from Review r where r.user.userid = :userid AND r.space.sseq = :sseq")
    int getRevewRate(@Param("userid") String userid, @Param("sseq") int sseq);

    @Query("select count(r) > 0 from Review r where r.user.userid = :userid AND r.space.sseq = :sseq order by r.rseq desc ")
    boolean getBooleanWrite(@Param("userid") String userid, @Param("sseq") int sseq);


    @Query("select  coalesce(cast(avg(r.rate) as int), 0) from Review r where r.space.sseq = :sseq")
    int getAllReivewRateCount(@Param("sseq") int sseq);

    @Query("select count(r) from Review r where r.space.sseq = :sseq ")
    int getAllReivewCount(@Param("sseq") int sseq);

    int countBySpaceSseq(int sseq);

    Page<Review> findBySpaceSseq(int sseq, Pageable pageable);


//    ArrayList findBySseq(int sseq);
}
