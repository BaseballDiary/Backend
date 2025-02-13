package com.backend.baseball.Community.entity;

import com.backend.baseball.Login.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Likes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesCertificateId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="certificate_id",nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="post_certificate_id",nullable=false)
    private Post post;
}
