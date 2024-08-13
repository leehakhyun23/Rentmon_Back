package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "host")
@Getter
@Setter
public class Host {
    @Id
    @Column(name = "hostid")
    private String hostid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

//    @Column(name = "name", updatable = false, length = 20)
//    private String name;
//
//    @Column(name = "phone", nullable = false, length = 20)
//    private String phone;
//
//    @Column(name = "email", nullable = false, length = 50)
//    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @ManyToOne
    @JoinColumn(name = "mseq")
    private Member member;

    @Column(name = "provider")
    private String provider;

//    @Column(name = "snsid")
//    private String snsid;

    @Column(name = "nickname")
    private String nickname;

}
