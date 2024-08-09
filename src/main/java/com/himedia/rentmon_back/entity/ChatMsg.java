package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Table(name = "Chatmsg")
@Getter
public class ChatMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmseq")
    private int cmseq;

    @Column(name = "isfid")
    private String isfid;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private String created_at;

    @Column(name = "crseq")
    private int crseq;

}
