package com.backend.baseball.Community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.backend.baseball.User.entity.User;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentCertificateId;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name="post_certificate_id",nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name="certificate_id",nullable = false)
    private User user;

}
