package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Admin")
@Getter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminid")
    private String adminid;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "mseq")
    private int mseq;
}
