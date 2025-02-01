package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class PitcherRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitcherCertificatedId; //투수 식별 번호

    @Column(nullable = false)
    private String year; //년도

    @Column(nullable = false)
    private String playerName; // 선수 이름

    @Column(nullable = false)
    private String ranking; // 순위

    @Column(nullable = false)
    private String club; //구단

    @Column(nullable = false)
    private String era; // 평균자책

    @Column(nullable = false)
    private String gameNum; // 경기수

    @Column(nullable = false)
    private String win; // 승

    @Column(nullable = false)
    private String lose; // 패

    @Column(nullable = false)
    private String inning; // 이닝

    @Column(nullable = false)
    private String save; //세이브

    @Column(nullable = false)
    private String hold; //홀드

    @Column(nullable = false)
    private String pitchCount; //투구수

    @Column(nullable = false)
    private String hitsAllowed; //피안타

    @Column(nullable = false)
    private String homeRunsAllowed; //피홈런

    @Column(nullable = false)
    private String strikeout; //탈삼진

    @Column(nullable = false)
    private String walk; //사시구

    @Column(nullable = false)
    private String runsAllowed; //실점

    @Column(nullable = false)
    private String earnedRun; //자책

    @Column(nullable = false)
    private String whip; //WHIP

    @Column(nullable = false)
    private String qs; //QS
}
