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
    @Column(name = "c_seq")
    private int cseq;

    @JoinColumn(name = "b_num")
    @Column(name = "b_num")
    private int bnum;

    @Column(name = "cardnum", nullable = false, length = 20)
    private String cardnum;

    @Column(name = "monthYear", nullable = false)
    private int monthYear;

    @Column(name = "cvc", nullable = false)
    private int cvc;
}
