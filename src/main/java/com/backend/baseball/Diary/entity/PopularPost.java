package com.backend.baseball.Diary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class PopularPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long popularPostId;

    @Column(nullable = false)
    private LocalDateTime addedAt; //인기 게시글에 추가된 시간

    @Column(nullable = false)
    private LocalDateTime expiredAt; //인기 게시글에서 삭제 예정 시간
}
