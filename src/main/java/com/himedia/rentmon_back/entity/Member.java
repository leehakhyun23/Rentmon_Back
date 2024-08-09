package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {
    @Id
    @Column(name = "mseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mseq;

    @Column(name = "userid", nullable = false, length = 50)
    private String userid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

}
