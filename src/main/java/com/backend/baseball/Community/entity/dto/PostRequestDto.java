package com.backend.baseball.Community.entity.dto;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.TeamCategory;
import lombok.Getter;
import lombok.Setter;
import com.backend.baseball.User.entity.User;

@Setter
@Getter
public class PostRequestDto {
    private Long postCertificateId;
    private String title;
    private String contents;
    private User user;
    private int views;
    private TeamCategory teamCategory;

    public Post toEntity(){
        Post post=Post.builder()
                .title(title)
                .contents(contents)
                .views(0)
                .user(user)
                .teamCategory(teamCategory)
                .build();
        return post;
    }
}
