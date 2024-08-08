package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.SpaceFacility;
import com.himedia.rentmon_back.entity.Spaceimage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SpaceDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class SpaceList{
        // space 테이블에서 획득
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

        // review 테이블
        private int reviewCount;

        // zzim 테이블
        private int zzimCount;

        // spaceimage 테이블
        private List<String> spaceImages;

        // spacehash 테이블
        private List<String> spaceHashTags;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseSpaceView {
        private int sseq;
        private String title;
        private String subTitle;

        private List<SpaceFacility> facilities;
        private List<Spaceimage> images;
    }
}
