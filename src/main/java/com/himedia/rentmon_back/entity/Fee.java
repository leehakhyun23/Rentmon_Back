package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "Fee")
@Getter
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fnum")
    private int fnum;

    @Column(name = "name")
    private String name;

}
