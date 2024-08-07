package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "Hashspace")
@Getter
public class HashSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hsseq")
    private int hsseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "hseq")
    private int hseq;

}
