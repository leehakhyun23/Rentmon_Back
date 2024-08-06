package com.himedia.rentmon_back.entity;

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

    @Column(name = "originame")
    private String origiName;

    @Column(name = "realname")
    private String realName;

    @Column(name = "titleyn")
    private boolean titleYn;

    @Column(name = "extension")
    private String extension;

    @Column(name = "size")
    private Long size;

    @Column(name = "created_at")
    private Timestamp created_at;

}
