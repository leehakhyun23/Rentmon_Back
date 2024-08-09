package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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

    @Column(name = "userid")
    private String userid;

    @Column(name = "pwd")
    private String pwd;

    @Column(name = "role")
    private String role;


    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

}
