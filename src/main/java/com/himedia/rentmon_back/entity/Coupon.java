package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "coupon")
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cseq")
    private int cseq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "couponstr", nullable = false, length = 30)
    private String couponstr;

    @Column(name = "discount", nullable = false)
    private int discount;

    @Column(name = "limitdate", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDate limitdate;

    @Column(name = "useyn", nullable = false, columnDefinition = "integer default 1")
    private boolean useyn;
}
