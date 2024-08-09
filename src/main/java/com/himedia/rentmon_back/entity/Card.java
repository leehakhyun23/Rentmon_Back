package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Card")
@Getter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_seq")
    private int cseq;

    @Column(name = "b_num")
    private int bnum;

    @Column(name = "cardnum")
    private String cardnum;

    @Column(name = "monthYear")
    private int monthYear;

    @Column(name = "cvc")
    private int cvc;
}
