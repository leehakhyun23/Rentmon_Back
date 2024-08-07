package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "Fee")
@Getter
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_num")
    private int f_num;

    @Column(name = "f_name")
    private String f_name;

}
