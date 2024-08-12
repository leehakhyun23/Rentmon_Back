package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "accountnum")
@Getter
@Setter
public class Accountnum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aseq")
    private int aseq;

    @ManyToOne
    @JoinColumn(name = "bnum")
    private Bank bank;

    @Column(name = "accountnum")
    private String accountnum;
}