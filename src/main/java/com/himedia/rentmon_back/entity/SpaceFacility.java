package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Spacefacility")
@Getter
public class SpaceFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sfseq")
    private int sfseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "fnum")
    private int fnum;
}
