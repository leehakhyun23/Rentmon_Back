package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "host")
@Getter
@Setter
@ToString
public class Host {
    @Id
    @Column(name = "hostid")
    private String hostid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

//    @Column(name = "name", updatable = false, length = 20)
//    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;


    @ManyToOne
    @JoinColumn(name = "mseq")
    private Member member;

    @Column(name = "provider")
    private String provider;

//    @Column(name = "snsid")
//    private String snsid;

    @Column(name = "nickname")
    private String nickname;


    public Host() {
        this.member = new Member();
    }

    public void setMseq(int mseq) {this.member.setMseq(mseq);}
}
