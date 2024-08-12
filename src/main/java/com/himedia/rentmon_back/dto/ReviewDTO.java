package com.himedia.rentmon_back.dto;

import com.himedia.rentmon_back.entity.ReviewImage;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    @Id
    private int rseq;
    private String content;
    private int rate;
    private String reply;
    private Timestamp replydate;
    private int sseq;
    private String userid;
    private Timestamp created_at;

    private List<String> images;



}
