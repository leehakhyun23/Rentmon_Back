package com.himedia.rentmon_back.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDTO {
    @Id
    private String userid;
}
