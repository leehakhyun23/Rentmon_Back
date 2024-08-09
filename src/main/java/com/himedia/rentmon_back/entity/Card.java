package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Card")
@Getter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cseq")
    private int cseq;

    @Column(name = "bnum")
    private int bnum;

    @Column(name = "cardnum")
    private String cardnum;

    @Column(name = "monthyear")
    private int monthyear;

    @Column(name = "cvc")
    private int cvc;
}
