package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;


@Entity
@Table(name = "declaration")
@Getter
@Setter
public class Declaration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dseq")
    private int dseq;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

    @Column(name = "reply")
    private String reply;

    @Column(name = "replydate", nullable = false, columnDefinition = "TIMESTAMP")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp replydate;

    @JoinColumn(name = "writer")
    @ManyToOne
    private Member writer;

    @JoinColumn(name = "sseq")
    @ManyToOne
    private Space sseq;
}
