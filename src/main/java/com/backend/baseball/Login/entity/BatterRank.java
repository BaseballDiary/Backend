package com.backend.baseball.Login.entity;

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
    private Long id; // 투수 개인순위 식별 번호(이건 erd에 없긴 했는데 일단 추가함)

    @Column(nullable = false,length=30)
    private String playerNameK; // 선수 이름

    @Column(nullable = false,columnDefinition = "integer default 1")
    private int rank; // 순위

    @Column(nullable = false,precision=3,scale=2)
    private float era; // 평균자책

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int field; // 경기

    @Column(nullable = false,columnDefinition = "integer default 1")
    private int win; // 승

    @Column(nullable = false,columnDefinition = "integer default 1")
    private int tie; // 무

    @Column(nullable = false,columnDefinition = "integer default 1")
    private int lose; // 패

    @Column(nullable = false,length=30)
    private String inning; // 이닝

}
