package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Hashsearch")
@Getter
@Setter
public class HashSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hsseq")
    private int hsseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "hseq")
    private int hseq;

    @Column(name = "word")
    private String word;
}
