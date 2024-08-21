package com.himedia.rentmon_back.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.himedia.rentmon_back.entity.Grade;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
public class AdminDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseUser {
        private String userid;
        private String name;
        private String phone;
        private String email;
        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Timestamp createdAt;
        private boolean isLogin;
        private String gname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseHost {
        private String hostid;
        private String nickname;
        private String category;
        private String title;
        private Integer price;
        private String phone;
        private String email;
        private String province;
        private String town;
        private String village;
        private String addressdetail;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IssuedCoupon {
        private List<String> userids;
        private LocalDate limitDate;
        private Integer discount;
        private String couponTitle;
    }
}
