package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bank")
@Getter
@Data
@Setter
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bnum")
    private int bnum;

    @Column(name = "bank", nullable = false, length = 20)
    private String bank;
}
