package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
<<<<<<< HEAD:src/main/java/com/himedia/rentmon_back/entity/Spaceimage.java
@Table(name = "spaceimage")
@Getter
public class SpaceImage {
=======
@Table(name = "Reservation")
@Getter
public class Reservation {
>>>>>>> danbi:src/main/java/com/himedia/rentmon_back/entity/Reservation.java
    @Id
    @Column(name = "rseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rseq;

    @Column(name = "reverdate")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp reverdate;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

    @Column(name = "hostid")
    private String hostid;

    @Column(name = "sseq")
    private int sseq;

<<<<<<< HEAD:src/main/java/com/himedia/rentmon_back/entity/Spaceimage.java
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

=======
>>>>>>> danbi:src/main/java/com/himedia/rentmon_back/entity/Reservation.java
}
