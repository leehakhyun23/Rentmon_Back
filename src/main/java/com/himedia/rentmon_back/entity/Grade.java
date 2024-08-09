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

    @Column(name = "gname", nullable = false, length = 20)
    private String gname;

    @Column(name = "sale", nullable = false)
    private int sale;
}
