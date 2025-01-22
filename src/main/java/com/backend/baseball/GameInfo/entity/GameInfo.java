package com.backend.baseball.GameInfo.entity;

import com.backend.baseball.Diary.entity.Diary;
import com.backend.baseball.GameInfo.enums.GameStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class GameInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameCertificateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String gameStatus; //경기 상태

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String team1; //팀1

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String team2; //팀2

    @Column(nullable = true)
    private String team1Score; //팀1 점수

    @Column(nullable = true)
    private String team2Score; //팀2 점수

    @Column(nullable = false)
    private LocalDate gameDate; //경기 날짜

    @Column(nullable = false, length = 40)
    String place; //경기 장소

    @Column(nullable = false, length = 20)
    String time; //경기 시간

    @OneToOne(mappedBy = "gameInfo")
    private Diary diary;
}
