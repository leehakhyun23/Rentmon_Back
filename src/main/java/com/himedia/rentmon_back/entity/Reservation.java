package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Entity
<<<<<<< HEAD:src/main/java/com/himedia/rentmon_back/entity/Spaceimage.java
@Table(name = "spaceimage")
@Getter
public class SpaceImage {
=======
@Table(name = "Reservation")
@Data
public class Reservation {
>>>>>>> danbi:src/main/java/com/himedia/rentmon_back/entity/Reservation.java
    @Id
    @Column(name = "rseq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rseq;

    @Column(name = "reservestart")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp reservestart;

    @Column(name = "reserveend")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp reserveend;

    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;

    @Column(name = "userid")
    private String userid;

    @Column(name = "sseq")
    private int sseq;


    @ManyToOne
    @JoinColumn(name = "sseq", insertable = false, updatable = false)
    private Space space;


    @OneToMany(mappedBy = "sseq", cascade = CascadeType.ALL)
    private List<Spaceimage> spaceimage;


}
