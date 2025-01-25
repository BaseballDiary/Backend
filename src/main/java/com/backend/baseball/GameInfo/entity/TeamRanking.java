package com.backend.baseball.GameInfo.entity;

import com.backend.baseball.GameInfo.enums.GameStatus;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TeamRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamRankCertificateId;

    @Column(nullable = false,columnDefinition = "integer default 1")
    private String ranking; // 순위

    private String teamName; //팀 이름

    @Column(nullable = false)
    private String gameNum; // 경기 수

    @Column(nullable = false)
    private String win; // 승

    @Column(nullable = false)
    private String tie; // 무

    @Column(nullable = false)
    private String lose; // 패

    @Column(nullable = false)
    private String winningRate; //승률

    @Column(nullable = false)
    private String gameDiff; //게임차

    @Column(nullable = false)
    private String streak; //연속

    @Column(nullable = false)
    private String odp; //출루율

    @Column(nullable = false)
    private String slg; //장타율

    @Column(nullable = false)
    private String last10Games; //최근 10 경기
}
