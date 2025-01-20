package com.backend.baseball.Diary.entity;

import com.backend.baseball.Diary.enums.ViewType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long diaryId; //일기 식별번호

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private ViewType viewType;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private String myTeam;

    @Column(nullable = false)
    private String opponentTeam;

    @Column(nullable = false)
    private Integer myTeamScore;

    @Column(nullable = false)
    private Integer opponentTeamScore;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDateTime time;

}
