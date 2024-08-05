package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "mseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mseq;

    @Column(name = "userid")
    private String userid;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "role")
    private String role;


    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp created_at;

}
