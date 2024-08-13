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

    @OneToOne
    @JoinColumn(name = "sseq")
    private Space sseq;

    @OneToMany
    @JoinColumn(name = "hseq")
    private List<Hashtag> hseq;
}
