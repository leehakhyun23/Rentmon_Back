package com.himedia.rentmon_back.dto;

import jakarta.persistence.Id;
import com.himedia.rentmon_back.entity.SpaceFacility;
import com.himedia.rentmon_back.entity.SpaceImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
        private int sseq;
        private String title;
        private String content;
        private int price;
        private String hostid;
        private int c_num;

        private String province;
        private String town;
        private String village;

        private int reviewCount;
        private int zzimCount;

        private Timestamp created_at;

        private List<String> spaceImages;
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
        private List<SpaceImage> images;
    }
}
