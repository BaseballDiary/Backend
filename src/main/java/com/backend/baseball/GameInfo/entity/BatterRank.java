package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class BatterRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batterCertificatedId; //타자 식별 번호

    @Column(nullable = false)
    private String playerName; // 선수 이름

    @Column(nullable = false)
    private Integer rank; // 순위

    @Column(nullable = false)
    private Integer gameNum; // 경기수

    @Column(nullable = false)
    private Integer atBat; //타석

    @Column(nullable = false)
    private Integer plateAppear; //타수

    @Column(nullable = false)
    private Integer single; //안타

    @Column(nullable = false)
    private Integer doubleHit; //2타

    @Column(nullable = false)
    private Integer tripleHit; //3타

    @Column(nullable = false)
    private Integer homeRun; //홈런

    @Column(nullable = false)
    private Integer rbis; //타점

    @Column(nullable = false)
    private Integer run; //득점

    @Column(nullable = false)
    private Integer stolenBase; //도루

    @Column(nullable = false)
    private Integer walk; //사시구

    @Column(nullable = false)
    private Integer strikeout; //삼진

    @Column(nullable = false)
    private float ba; //타율

    @Column(nullable = false)
    private float odp; //출루율

    @Column(nullable = false)
    private float slg; //장타율

    @Column(nullable = false)
    private Integer ops; //OPS
}