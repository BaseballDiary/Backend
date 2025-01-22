package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BatterRecordRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pitcherRecordCertificatedId; //투수 기록별 랭킹 식별 번호

    @Column(nullable = false)
    private String year; //년도

    @Column(nullable = false)
    private String recordType; //기록 종류

    @Column(nullable = false)
    private String ranking; //순위

    @Column(nullable = false)
    private String playerName; //선수 이름

    @Column(nullable = false)
    private String club; //선수 구단

    @Column(nullable = false)
    private String score; //점수
}
