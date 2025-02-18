package com.backend.baseball.Community.entity.dto;

import com.backend.baseball.Community.entity.Post;
import lombok.Getter;
import lombok.Setter;
import com.backend.baseball.User.entity.User;

@Setter
@Getter
public class PostRequestDto {
    private Long postCertificateId;
    private String contents;
    private int likes;
    private User user;
    private String teamClub;

    public Post toEntity(){
        Post post=Post.builder()
                .contents(contents)
                .likes(0)
                .user(user)
                .teamClub(teamClub)
                .build();
        return post;
    }
}
