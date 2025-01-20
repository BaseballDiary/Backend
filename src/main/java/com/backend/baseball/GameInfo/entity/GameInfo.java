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
public class GameInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameCertificateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus gameStatus; //경기 상태

    @Column(nullable = false, length =40)
    private String team1; //팀1

    @Column(nullable = false, length =40)
    private String team2; //팀2

    @Column(nullable = true)
    private Integer team1Score; //팀1 점수

    @Column(nullable = true)
    private Integer team2Score; //팀2 점수

    @ManyToOne
    @JoinColumn(name = "dateCertificateId", nullable=false)
    private DateInfo dateInfo ; //날짜 식별 번호

    @Column(nullable = false, length = 40)
    String place; //경기 장소

    @Column(nullable = false, length = 20)
    String time; //경기 시간

}
