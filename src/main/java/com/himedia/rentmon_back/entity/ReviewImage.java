package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reviewimage")
@Getter
@Setter
public class ReviewImage {
    @Id
    @Column(name = "riseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riseq;

    @ManyToOne
    @JoinColumn(name = "rseq")
    @JsonIgnore
    private Review review;

    @Column(name = "originname")
    private String originname;

    @Column(name = "realname")
    private String realname;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Integer size;
}
