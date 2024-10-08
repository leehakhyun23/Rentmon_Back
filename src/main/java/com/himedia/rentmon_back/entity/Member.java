package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "member")
@Getter
@Setter
@AllArgsConstructor
public class Member {
    @Id
    @Column(name = "mseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mseq;

    @Column(name = "userid")
    private String userid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

    @Column(name = "role")
    private String role;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp createdAt;

    public Member() {}
}
