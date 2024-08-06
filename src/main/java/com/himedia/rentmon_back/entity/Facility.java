package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;


@Entity
@Table(name = "Facility")
@Getter
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "f_num")
    private int f_num;

    @Column(name = "f_name")
    private String f_name;

    @Column(name = "f_icon")
    private String f_icon;



}
