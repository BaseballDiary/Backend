package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BatterRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batterCertificatedId; //타자 식별 번호

    @Column(nullable = false)
    private String year; //년도

    @Column(nullable = false)
    private String playerName; // 선수 이름

    @Column(nullable = false)
    private String ranking; // 순위

    @Column(nullable = false)
    private String club; //구단

    @Column(nullable = false)
    private String gameNum; // 경기수

    @Column(nullable = false)
    private String atBat; //타석

    @Column(nullable = false)
    private String plateAppear; //타수

    @Column(nullable = false)
    private String single; //안타

    @Column(nullable = false)
    private String doubleHit; //2타

    @Column(nullable = false)
    private String tripleHit; //3타

    @Column(nullable = false)
    private String homeRun; //홈런

    @Column(nullable = false)
    private String rbis; //타점

    @Column(nullable = false)
    private String run; //득점

    @Column(nullable = false)
    private String stolenBase; //도루

    @Column(nullable = false)
    private String walk; //사시구

    @Column(nullable = false)
    private String strikeout; //삼진

    @Column(nullable = false)
    private String ba; //타율

    @Column(nullable = false)
    private String obp; //출루율

    @Column(nullable = false)
    private String slg; //장타율

    @Column(nullable = false)
    private String ops; //OPS
}