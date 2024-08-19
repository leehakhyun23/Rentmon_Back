package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "space")
@Getter
@Setter
@Data
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sseq")
    private int sseq;               // 기본키

    @ManyToOne
    @JoinColumn(name = "hostid")
    private Host host;          // 호스트아이디

    @ManyToOne
    @JoinColumn(name = "cnum")
    private Category category;              // 분류 아이디

    @Column(name = "price")
    private int price;              // 가격

    @Column(name = "title")
    private String title;           // 제목

    @Column(name = "subtitle")
    private String subtitle;        // 부제

    @Column(name = "content")
    private String content;         // 내용

    @Column(name = "starttime")
    private Integer starttime;    // 이용시작 시간

    @Column(name = "endtime")
    private Integer endtime;      // 마감시간

    @Column(name = "caution")
    private String caution;         // 주의사항

    @Column(name = "zipcode")
    private String zipcode;         // 우편번호

    @Column(name = "province")
    private String province;        // 시도군

    @Column(name = "town")
    private String town;            // 구읍?

    @Column(name = "village")
    private String village;         // 동면리

    @Column(name = "addressdetail")
    private String addressdetail;  // 상세주소

    @Column(name = "maxpersonnal")
    private int maxpersonnal;      // 최대인원

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;   // 등록일

    public Space() {
        this.created_at = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SpaceImage> images;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Review> reviews;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SpaceFacility> facilities;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Zzim> zzims;
}
