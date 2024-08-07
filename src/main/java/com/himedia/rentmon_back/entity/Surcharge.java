package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Surcharge")
@Getter
public class Surcharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scseq")
    private int scseq;

    @Column(name = "situation")
    private String situation;

    @Column(name = "price")
    private int price;

    @Column(name = "sseq")
    private int sseq;



}
