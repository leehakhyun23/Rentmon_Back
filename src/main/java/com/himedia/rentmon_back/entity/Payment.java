package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "Payment")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "hostid")
    private Host host;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space space;
}
