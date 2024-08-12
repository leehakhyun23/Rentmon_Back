package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "spaceimage")
@Getter
@Setter
public class SpaceImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "siseq")
    private Integer siseq;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space space;

    @Column(name = "originame")
    private String origiName;

    @Column(name = "realname")
    private String realName;

    @Column(name = "titleyn")
    private boolean titleYn;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private long size;

    @Column(name = "create_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp create_at;
}