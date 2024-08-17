package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class RequestCoupon {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class IssuedCoupon {
        private List<String> userids;
        private LocalDate limitDate;
        private Integer discount;
    }
}
