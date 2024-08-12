package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Reviewimage")
@Getter
@Setter
public class ReviewImage {
    @Id
    @Column(name = "riseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riseq;

    @Column(name = "originname")
    private String originname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private int size;

    @ManyToOne
    @JoinColumn(name = "rseq")
    private Review review;




}
