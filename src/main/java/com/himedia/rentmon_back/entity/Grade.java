package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "Grade")
@Getter
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "g_num")
    private int g_num;

    @Column(name = "g_name")
    private String g_name;

    @Column(name = "sale")
    private int sale;
}
