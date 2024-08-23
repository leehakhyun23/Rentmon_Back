package com.himedia.rentmon_back.dto;

import lombok.Data;

@Data
public class SpaceUpdateRequest {
    private String title;
    private String subtitle;
    private int price;
    private int maxpersonnal;
    private String content;
    private String caution;
    private String zipcode;
    private String province;
    private String town;
    private String village;
    private String addressdetail;
    private String address;
    private int starttime;
    private int endtime;
}