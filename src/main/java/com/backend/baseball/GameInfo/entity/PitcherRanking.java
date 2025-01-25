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
public class PitcherRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitcherCertificatedId; //투수 식별 번호

    @Column(nullable = false)
    private String playerName; // 선수 이름

    @Column(nullable = false)
    private Integer ranking; // 순위

    @Column(nullable = false)
    private float era; // 평균자책

    @Column(nullable = false)
    private Integer gameNum; // 경기수

    @Column(nullable = false)
    private Integer win; // 승

    @Column(nullable = false)
    private Integer lose; // 패

    @Column(nullable = false)
    private String inning; // 이닝

    @Column(nullable = false)
    private Integer save; //세이브

    @Column(nullable = false)
    private Integer hold; //홀드

    @Column(nullable = false)
    private Integer pitchCount; //투구수

    @Column(nullable = false)
    private Integer hitsAllowed; //피안타

    @Column(nullable = false)
    private Integer homeRunsAllowed; //피홈런

    @Column(nullable = false)
    private Integer strikeout; //탈삼진

    @Column(nullable = false)
    private Integer walk; //사시구

    @Column(nullable = false)
    private Integer runsAllowed; //실점

    @Column(nullable = false)
    private float earnedRun; //자책

    @Column(nullable = false)
    private float whip; //WHIP

    @Column(nullable = false)
    private Integer qs; //QS
}
