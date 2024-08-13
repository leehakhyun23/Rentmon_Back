package com.himedia.rentmon_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Spacefacility")
@Getter
@Setter
public class SpaceFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sfseq")
    private int sfseq;

    @ManyToOne
    @JoinColumn(name = "sseq")
    private Space space;

    @ManyToOne
    @JoinColumn(name = "fnum")
    private Facility facility;
}
