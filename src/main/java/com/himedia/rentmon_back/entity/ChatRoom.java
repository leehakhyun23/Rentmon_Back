package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "chatroom")
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmseq")
    private int cmseq;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "status")
    private String status;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private String created_at;

    @Column(name = "userid")
    private String userid;

    @Column(name = "hostid")
    private String hostid;

}
