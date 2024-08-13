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

    @ManyToOne
    @JoinColumn(name = "bnum")
    private Bank bank;

    @Column(name = "cardnum", nullable = false, length = 20)
    private String cardnum;

    @Column(name = "monthyear", nullable = false, length = 4)
    private String monthyear;

    @Column(name = "cvc", nullable = false, length = 3)
    private int cvc;
}
