package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Zzim")
@Getter
@Setter
public class Zzim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zseq")
    private int zseq;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space space;
}
