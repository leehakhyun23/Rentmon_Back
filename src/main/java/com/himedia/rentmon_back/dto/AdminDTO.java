package com.himedia.rentmon_back.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;

@Data
public class AdminDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseHost {
        private String hostid;
        private String name;
        private String category;
        private String fee;
        private int price;
        private String phone;
        private String province;
        private String town;
        private String village;
        private String addressDetail;
        private int declaCount;
//        private boolean isShow;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDeclaration {
        private int dseq;
        private String reply;
        private String reporter;
        private String title;
//        private String reported;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp created_at;
    }
}
