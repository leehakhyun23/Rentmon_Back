package com.himedia.rentmon_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "HASHSEARCH")
@Getter
public class HashSearch {

    @Id
    private int hsseq;
    private int sseq;
    private int hseq;
    private String word;
}
