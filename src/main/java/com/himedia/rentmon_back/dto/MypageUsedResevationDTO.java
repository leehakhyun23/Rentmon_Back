package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.Reservation;
import com.himedia.rentmon_back.entity.Space;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MypageUsedResevationDTO {
    private Reservation reservation;
    private boolean writereview;
    private int reveiwreater;


}
