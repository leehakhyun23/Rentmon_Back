package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
public class Admin {
    @Id
    @Column(name = "adminid", length = 50)
    private String adminid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

    @ManyToOne
    @JoinColumn(name = "mseq")
    private Member member;
}
