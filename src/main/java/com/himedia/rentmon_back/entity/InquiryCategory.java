package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "InquiryCategory")
@Getter
public class InquiryCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ic_num")
    private int ic_num;

    @Column(name = "ic_name")
    private String ic_name;
}
