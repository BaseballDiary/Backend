package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class TeamRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRankCertificateId;



    @Column(nullable = false,precision=3,scale=2)
    private float era; // 평균자책

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int field; // 경기

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int win; // 승

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int tie; // 무

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int lose; // 패

    @Column(nullable = false,columnDefinition = "integer default 1")
    private int rank; // 순위

}
