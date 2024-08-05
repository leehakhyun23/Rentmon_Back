package com.himedia.rentmon_back.util;

import com.himedia.rentmon_back.security.util.CustomJWTException;
import com.himedia.rentmon_back.security.util.JWTUtil;

import java.sql.Date;

public class TokenRefreshUtil {

    public boolean checkTime(Integer exp) {
        Date expDate = new Date((long)exp * (1000) );//밀리초로 변환
        long gap = expDate.getTime() - System.currentTimeMillis();//현재 시간과의 차이 꼐산
        long leftMin = gap / (1000*60); //분단위 계산
        //1시간도 안남았는지
        return leftMin < 60;
    }

    public boolean checkExpriedToken(String accessToken) {
        try {
            JWTUtil.validateToken(accessToken);
        }catch (CustomJWTException ex){
            if(ex.getMessage().equals("Expired")) return true;
        }
        return false;
    }
}
