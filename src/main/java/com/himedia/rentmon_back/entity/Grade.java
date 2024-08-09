package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "Grade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gnum")
    private int gnum;

    @Column(name = "gname")
    private String gname;

    @Column(name = "sale")
    private int sale;
}
