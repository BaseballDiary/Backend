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

    @Column(nullable = false,columnDefinition = "integer default 1")
    private Integer rank; // 순위

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus teamName; //팀 이름

    @Column(nullable = false)
    private Integer gameNum; // 경기 수

    @Column(nullable = false)
    private Integer win; // 승

    @Column(nullable = false)
    private Integer tie; // 무

    @Column(nullable = false)
    private Integer lose; // 패

    @Column(nullable = false)
    private Double winningRate; //승률

    @Column(nullable = false)
    private Double gameDiff; //게임차

    @Column(nullable = false)
    private String streak; //연속

    @Column(nullable = false)
    private Double odp; //출루율

    @Column(nullable = false)
    private Double slg; //장타율

    @Column(nullable = false)
    private String last10Games; //최근 10 경기
}
