package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Inquirycategory")
@Getter
@Setter
public class InquiryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "icnum")
    private int icnum;

    @Column(name = "name")
    private String name;
}
