package com.himedia.rentmon_back.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Timestamp;

@Entity
@Table(name = "Space")
@Getter
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sseq")
    private int sseq;               // 기본키

    @Column(name = "hostid")
    private String hostid;          // 호스트아이디
    @Column(name = "c_num")
    private int c_num;              // 분류 아이디
    @Column(name = "f_num")
    private int f_num;              // 요금제 아이디
    @Column(name = "price")
    private int price;              // 가격
    @Column(name = "title")
    private String title;           // 제목
    @Column(name = "subTitle")
    private String subTitle;        // 부제
    @Column(name = "content")
    private String content;         // 내용
    @Column(name = "startTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp startTime;    // 이용시작 시간
    @Column(name = "endTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp endTime;      // 마감시간
    @Column(name = "closed")
    private String closed;          // 휴일
    @Column(name = "caution")
    private String caution;         // 주의사항
    @Column(name = "zipCode")
    private String zipCode;         // 우편번호
    @Column(name = "province")
    private String province;        // 시도군
    @Column(name = "town")
    private String town;            // 구읍?
    @Column(name = "village")
    private String village;         // 동면리
    @Column(name = "address_detail")
    private String address_detail;  // 상세주소
    @Column(name = "min_time")
    private int min_time;           // 최소이용시간
    @Column(name = "personnal")
    private int personnal;          // 인원
    @Column(name = "max_personnal")
    private int max_personnal;      // 최대인원
    @Column(name = "created_at")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Timestamp created_at;   // 등록일
}
