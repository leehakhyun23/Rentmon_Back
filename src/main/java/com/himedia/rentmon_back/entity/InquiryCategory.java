package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "Inquirycategory")
@Getter
public class InquiryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icnum")
    private int icnum;

    @Column(name = "name")
    private String name;
}
