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
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postCertificateId;

    @Column(length=500,nullable=false)
    private String title;

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

    @ManyToOne(fetch=FetchType.LAZY)
    private TeamCategory teamCategory;

    public void increaseLikes(){
        this.likes+=1;
    }
    public void decreaseLikes(){
        this.likes = Math.max(0, this.likes - 1);
    }

    public void update(String title,String contents){
        this.title = title;
        this.contents = contents;
    }
}
