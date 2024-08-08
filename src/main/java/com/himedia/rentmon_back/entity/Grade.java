package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "grade")
@Getter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gnum")
    private int gnum;

    @Column(name = "gname")
    private String gname;

    @Column(name = "sale")
    private int sale;
}
