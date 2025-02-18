package com.backend.baseball.Community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.backend.baseball.User.entity.User;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCertificateId;


    @Column(columnDefinition = "TEXT",nullable = false)
    private String contents;

    @Column(columnDefinition = "integer default 0",nullable = false)
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="certificate_id")
    private User user;

    @OneToMany(mappedBy="post",cascade = CascadeType.REMOVE,orphanRemoval = true)
    @OrderBy("commentCertificateId asc")
    private List<Comment> comments;


    private String teamClub;

    public void increaseLikes(){
        this.likes+=1;
    }
    public void decreaseLikes(){
        this.likes = Math.max(0, this.likes - 1);
    }

}
