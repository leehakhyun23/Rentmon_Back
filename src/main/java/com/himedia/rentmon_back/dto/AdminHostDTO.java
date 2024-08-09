package com.himedia.rentmon_back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class AdminHostDTO {
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

    public AdminHostDTO() {}
}
