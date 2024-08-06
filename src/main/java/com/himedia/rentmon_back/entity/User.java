package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
public class User {
    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "mseq")
    private int mseq;

    @Column(name = "cseq")
    private int cseq;

    @Column(name = "gnum")
    private int gnum;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "category1")
    private String category1;

    @Column(name = "category2")
    private String category2;

    @Column(name = "category3")
    private String category3;

    @Column(name = "station")
    private String station;

    @Column(name = "provider")
    private String provider;

    @Column(name = "snsid")
    private String snsid;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp created_at;
}
