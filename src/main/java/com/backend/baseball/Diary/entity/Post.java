package com.backend.baseball.Diary.entity;
import com.backend.baseball.Login.enums.Club;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCertificateId; // 게시글 식별번호

    @Column(nullable = false,length=30)
    private String title; // 게시글 제목

    @Column(nullable = false)
    private String contents; // 게시글 내용 데이터타입 뭐로..


    @Column(nullable = false)
    private Timestamp createdAt; //게시글 생성 시간

    @Column(nullable = true)
    private Timestamp deletedAt; //게시글 삭제 시간

    @Column(nullable = false)
    private Club club; //이거 원래 category였는데 enum 사용 고려해서 category로 통일

    @Column(nullable = false)
    private String imgurl;

    @Column(nullable = false)
    private Long views;

    @Column(nullable = false)
    private Long likes;

}
