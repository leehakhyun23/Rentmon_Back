package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "Coupon")
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnum")
    private int cnum;

    @Column(name = "userid")
    private String userid;

    @Column(name = "couponstr")
    private String couponstr;

    @Column(name = "discount")
    private int discount;

    @Column(name = "limitdate")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp limitdate;

    @Column(name = "useyn")
    private boolean useyn;
}
