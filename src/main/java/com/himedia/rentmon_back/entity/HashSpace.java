package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import java.util.List;


@Entity
@Table(name = "Hashspace")
@Getter
@Setter
public class HashSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hsseq")
    private int hsseq;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space sseq;

    @ManyToOne
    @JoinColumn(name = "hseq")
    private Hashtag hseq;
}
