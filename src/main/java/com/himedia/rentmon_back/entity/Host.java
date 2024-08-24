package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Entity
@Table(name = "host")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Host {
    @Id
    @Column(name = "hostid")
    private String hostid;

    @Column(name = "pwd", nullable = false, length = 1000)
    private String pwd;

//    @Column(name = "name", updatable = false, length = 20)
//    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;


    @ManyToOne
    @JoinColumn(name = "mseq")
    private Member member;

    @Column(name = "provider")
    private String provider;

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Space> spaces;

    public Host() {
        this.member = new Member();
    }

    public Host(String hostid, String pwd, String email, String phone, Member member, String provider, String nickname) {
        this.hostid = hostid;
        this.pwd = pwd;
        this.email = email;
        this.phone = phone;
        this.member = member;
        this.provider = provider;
        this.nickname = nickname;
    }

    public void setMseq(int mseq) {this.member.setMseq(mseq);}


}
