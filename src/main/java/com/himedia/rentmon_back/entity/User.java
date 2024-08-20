package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "userid")
    private String userid;

    @ManyToOne
    @JoinColumn(name = "mseq")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "cseq")
    private Card cseq;

    @ManyToOne
    @JoinColumn(name = "gnum")
    private Grade gnum;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "profileimg")
    private String profileimg;

    @Column(name = "category1")
    private Integer category1;

    @Column(name = "category2")
    private Integer category2;

    @Column(name = "category3")
    private Integer category3;

    @Column(name = "station")
    private String station;

    @Column(name = "provider")
    private String provider;

    @Column(name = "snsid")
    private String snsid;

    @Column(name = "islogin", nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private boolean islogin;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp createdAt;

    public User(Member member) {
        this.member = member;
    }

    public void setMseq(int mseq) {
        this.member = new Member();
        this.member.setMseq(mseq);
    }
}
