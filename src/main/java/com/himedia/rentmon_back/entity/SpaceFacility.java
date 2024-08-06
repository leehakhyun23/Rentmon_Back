package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "SpaceFacility")
@Getter
public class SpaceFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sfseq")
    private int sfseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "f_num")
    private int f_num;
}
