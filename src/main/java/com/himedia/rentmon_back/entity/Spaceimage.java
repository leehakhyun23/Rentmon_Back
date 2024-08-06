package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "Spaceimage")
@Getter
public class SpaceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siseq")
    private int siseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "originalname")
    private String originalname;

    @Column(name = "titleyn")
    private int titleyn;

    @Column(name = "extension")
    private Timestamp extension;

    @Column(name = "size")
    private int size;

    @Column(name = "create_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private boolean create_at;


}
