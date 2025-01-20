package com.backend.baseball.Diary.entity;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long commentCertificatedId;

    @Column(nullable = false)
    private String contents;
}
