package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Hashtag")
@Getter
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hseq")
    private int hseq;

    @Column(name = "word", nullable = false, length = 50)
    private String word;
}
