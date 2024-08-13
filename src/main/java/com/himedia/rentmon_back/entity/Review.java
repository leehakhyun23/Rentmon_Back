package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Review")
@Getter
@Setter
public class Review {
    @Id
    @Column(name = "rseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rseq;

    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private int rate;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp created_at;

    @Column(name = "reply")
    private String reply;

    @Column(name = "replydate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp replydate;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "userid")
    private String userid;
}