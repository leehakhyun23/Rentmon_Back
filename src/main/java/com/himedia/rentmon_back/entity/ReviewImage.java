package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "ReviewImage")
@Getter
public class ReviewImage {
    @Id
    @Column(name = "riseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riseq;

    @Column(name = "rseq")
    private int rseq;

    @Column(name = "originname")
    private String originname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private int size;




}
