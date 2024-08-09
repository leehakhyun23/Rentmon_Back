package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gnum")
    private int gnum;

    @Column(name = "gname", nullable = false, length = 20)
    private String gname;

    @Column(name = "sale", nullable = false)
    private int sale;
}
