package com.himedia.rentmon_back.entity;

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
    private String hostid;          // 호스트아이디
    private int c_num;              // 분류 아이디
    private int f_num;              // 요금제 아이디
    private int price;              // 가격
    private String title;           // 제목
    private String subTitle;        // 부제
    private String content;         // 내용
    private Timestamp startTime;    // 이용시작 시간
    private Timestamp endTime;      // 마감시간
    private String closed;          // 휴일
    private String caution;         // 주의사항
    private String zipCode;         // 우편번호
    private String province;        // 시도군
    private String town;            // 구읍?
    private String village;         // 동면리
    private String address_detail;  // 상세주소
    private int min_time;           // 최소이용시간
    private int personnal;          // 인원
    private int max_personnal;      // 최대인원
    private Timestamp created_at;   // 등록일
}
