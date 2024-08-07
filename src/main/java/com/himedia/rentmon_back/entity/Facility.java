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
    @Column(name = "fnum")
    private int fnum;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;



}
