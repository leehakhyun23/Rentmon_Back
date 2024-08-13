package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "Reservation")
@Getter
@Setter
public class Reservation {
    @Id
    @Column(name = "rseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rseq;

    @Column(name = "price")
    private int price;

    @Column(name = "reservestart")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp reservestart;

    @Column(name = "reserveend")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp reserveend;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sseq", insertable = false, updatable = false)
    private Space space;

//    @OneToMany(mappedBy = "sseq", cascade = CascadeType.ALL)
//    private List<SpaceImage> spaceimage;
}
