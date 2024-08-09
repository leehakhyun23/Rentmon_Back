package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "card")
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cseq")
    private int cseq;

    @Column(name = "bnum")
    private int bnum;

    @Column(name = "cardnum", nullable = false, length = 20)
    private String cardnum;

    @Column(name = "monthyear")
    private int monthyear;

    @Column(name = "cvc", nullable = false)
    private int cvc;
}
