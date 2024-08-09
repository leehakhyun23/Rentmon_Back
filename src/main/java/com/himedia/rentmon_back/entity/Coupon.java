package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "coupon")
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cnum")
    private int cnum;

    @Column(name = "userid")
    private String userid;

    @Column(name = "couponstr", nullable = false, length = 30)
    private String couponstr;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "limitdate", nullable = false)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp limitdate;

    @Column(name = "useyn", nullable = false, columnDefinition = "integer default 1")
    private boolean useyn;
}
