package com.backend.baseball.domain;

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
public class BaseballClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubCertificateId; // 구단 식별번호

    @Column(nullable = false, unique = true,length=30)
    private String clubNameK; // 구단 이름 한글

    @Column(nullable = false, unique = true,length=30)
    private String clubNameE; // 구단 이름 영어

    // 이미지는 아직 안 적음
}
