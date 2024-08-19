package com.himedia.rentmon_back.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpaceImageDTO {
    private Integer sseq; // Space의 식별자
    private List<String> originalnames; // 원본 이미지 이름 리스트
    private List<String> realnames;     // 저장된 이미지 이름 리스트
}