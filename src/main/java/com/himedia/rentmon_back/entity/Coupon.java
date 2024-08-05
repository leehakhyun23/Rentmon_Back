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
    @Column(name = "c_num")
    private int c_num;

    @Column(name = "userid")
    private String userid;

    @Column(name = "couponStr")
    private String couponStr;

    @Column(name = "discount")
    private int discount;

    @Column(name = "limitDate")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp limitDate;

    @Column(name = "useYn")
    private boolean useYn;
}
