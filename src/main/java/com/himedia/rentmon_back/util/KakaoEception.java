package com.himedia.rentmon_back.util;

public class KakaoEception extends Throwable {
    private final String kakaodata;
    public KakaoEception(String message, String kakaodata) {
        super(message);
        this.kakaodata = kakaodata;
    }
}
