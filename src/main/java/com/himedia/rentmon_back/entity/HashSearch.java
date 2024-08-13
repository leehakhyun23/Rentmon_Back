package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Subselect("SELECT hs.hsseq, hs.sseq, hs.hseq h.word FROM hashspace hs JOIN hashtag h ON hs.hseq = h.hseq")
@Getter
@Setter
public class HashSearch {
    @Id
    @Column(name = "hsseq")
    private int hsseq;

    @Column(name = "sseq")
    private int sseq;

    @Column(name = "hseq")
    private int hseq;

    @Column(name = "word")
    private String word;
}
