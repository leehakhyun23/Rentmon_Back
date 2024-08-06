package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.SpaceFacility;
import com.himedia.rentmon_back.entity.SpaceImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class SpaceDTO {
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
