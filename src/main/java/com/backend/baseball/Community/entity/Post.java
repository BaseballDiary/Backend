package com.backend.baseball.Community.entity;

import com.backend.baseball.Login.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCertificateId;

    @Column(length=500,nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT",nullable=false)
    private String contents;

    @Column(columnDefinition = "integer default 0",nullable=false)
    private int views;// 조회수

    @Column(columnDefinition = "integer default 0",nullable = false)
    private int likeCount;

    @Column(nullable = false, length = 50)
    private String teamClub; // 🔹 구단 이름 추가

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="certificate_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("commentCertificateId asc")
    private List<Comment> comments;

    @ManyToOne()



    /*게시글 수정*/
    public void update(String title,String content){
        this.title = title;
        this.contents = content;
    }

}
