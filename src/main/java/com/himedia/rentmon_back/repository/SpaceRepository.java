package com.himedia.rentmon_back.repository;

import com.himedia.rentmon_back.entity.Host;
import com.himedia.rentmon_back.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Integer>, JpaSpecificationExecutor<Space> {
    Optional<Space> findBysseq(int sseq);


    @Query("select s from Space s order by s.created_at desc ")
    Page<Space> getSpaceRecent(Pageable pageable);

    @Query("select s from Space s where (s.province LIKE concat(:station, '%')  and s.category.cnum in(:category1 , :category2 , :category3)) OR (s.province LIKE concat('', '%')  and s.category.cnum in(:category1 , :category2 , :category3))  order by s.created_at desc ")
    Page<Space> getRecommandSpace(@Param("station") String station,@Param("category1") int category1,@Param("category2") int category2, @Param("category3") int category3, Pageable pageable);

    @Query("select s from Space s where s.category.cnum = :cnum order by s.created_at desc ")
    Page<Space> getCategoryList(Pageable pageable, int cnum);

    @Query("SELECT DISTINCT s.title FROM Space s WHERE s.host = :host")
    List<String> findDistinctTitlesByHost(@Param("host") Host host);

    List<Space> findByHost(Host host);


    Optional<Space> findBySseq(Integer sseq);
    @Query("SELECT s.sseq FROM Space s WHERE s.host = :host")
    List<Integer> findSseqsByHostId(@Param("host") Host host);

    Space findTitleBySseq(Integer sseq);
}

