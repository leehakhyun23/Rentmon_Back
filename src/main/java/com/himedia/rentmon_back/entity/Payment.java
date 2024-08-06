package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "Payment")
@Getter
public class Payment {
    @Id
    @Column(name = "pseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pseq;

    @Column(name = "price")
    private int price;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

    @Column(name = "hostid")
    private String hostid;

    @Column(name = "sseq")
    private int sseq;
}
