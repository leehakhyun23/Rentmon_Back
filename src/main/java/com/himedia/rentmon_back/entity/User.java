package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Getter
public class User {
    @Id
    @Column(name = "userid")
    private String userid;

    @Column(name = "mseq")
    private int mseq;

    @Column(name = "cseq")
    private int cseq;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "gnum")
    private Grade grade;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "profileimg")
    private String profileimg;

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

    @Column(name = "islogin")
    private boolean islogin;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;
}
