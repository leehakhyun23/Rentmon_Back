package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "spaceimage")
@Getter
public class SpaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siseq")
    private int siseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "originname")
    private String originame;

    @Column(name = "realname")
    private String realname;

    @Column(name = "titleyn")
    private boolean titleyn;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private int size;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;
}
