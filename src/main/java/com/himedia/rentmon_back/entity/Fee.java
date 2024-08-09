package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Fee")
@Getter
@Setter
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fnum")
    private int fnum;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

}
