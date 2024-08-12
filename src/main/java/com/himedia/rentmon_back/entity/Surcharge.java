package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Surcharge")
@Getter
@Setter
public class Surcharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scseq")
    private int scseq;

    @Column(name = "situation")
    private String situation;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space space;
}
