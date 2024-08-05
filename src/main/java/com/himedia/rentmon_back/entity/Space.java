package com.himedia.rentmon_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "Space")
@Getter
public class Space {
    private int sseq;
    private String hostid;
    private int mc_num;
    private int f_num;
    private int price;
    private String title;
    private String subTitle;
    private String content;
    private 
}
