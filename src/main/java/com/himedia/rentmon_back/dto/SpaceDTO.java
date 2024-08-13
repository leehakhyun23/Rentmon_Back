package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.*;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpaceDTO {
    //Space 테이블 조회
    @Id
    private int sseq;
    private String title;
    private String content;
    private int price;
    private String hostid;
    private int cnum;
    private String province;
    private String town;
    private String village;
    private Timestamp created_at;

    // spaceimage 테이블
    private List<SpaceImage> images;

    // spacehash 테이블
    private List<HashSpace> hashtags;

    // spaceFaciliity 테이블 조회
    private List<SpaceFacility> facilities;

    // surcharge 테이블 조회
    private List<Surcharge> surcharges;

    // zzim 테이블
    private int zzimCount;

    // review 리스트 조회
    private List<Review> reviews;

}
