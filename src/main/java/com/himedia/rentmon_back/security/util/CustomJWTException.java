package com.himedia.rentmon_back.security.util;

public class CustomJWTException extends Exception {
    public CustomJWTException(String msg) {
        super(msg);
    }
}
