package com.himedia.rentmon_back.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "Zzim")
@Getter
public class Zzim {
    @Id
    @Column(name = "zseq")
    private String zseq;

    @Column(name = "userid")
    private String userid;

    @Column(name = "sseq")
    private int sseq;


}
