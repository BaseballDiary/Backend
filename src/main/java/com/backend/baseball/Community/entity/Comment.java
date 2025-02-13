package com.backend.baseball.Community.entity;

import com.backend.baseball.Login.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentCertificateId;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentContent;// 댓글내용

    @ManyToOne
    @JoinColumn(name="post_certificate_id",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="certificate_id",nullable = false)
    private User user;

    /*댓글 수정하기*/
    public void update(String commentContent){
        this.commentContent = commentContent;
    }
}
