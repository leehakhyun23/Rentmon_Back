package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AdminDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseUser {
        private String userid;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ResponseHost {
        private String hostid;
        private String name;
    }
}
