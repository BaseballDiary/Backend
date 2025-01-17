package com.backend.baseball.GameInfo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class DateInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dateCertificateId; //날짜 식별 번호

    @Column(nullable = false)
    private LocalDate date; //날짜 값 ex)2025-01-17 형태
}

