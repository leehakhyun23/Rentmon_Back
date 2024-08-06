package com.himedia.rentmon_back.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String userid;  // username:role 형태로 전달
    private String pwd;
}
