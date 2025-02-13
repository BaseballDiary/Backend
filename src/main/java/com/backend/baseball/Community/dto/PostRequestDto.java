package com.backend.baseball.Community.dto;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Login.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {
    private Long postCertificateId;
    private String title;
    private String contents;
    private int views;
    private User user;
    private String teamClub;

    /*Dto -> Entity*/
    public Post toEntity(){
        Post post=Post.builder()
                .postCertificateId(postCertificateId)
                .title(title)
                .contents(contents)
                .views(0)
                .user(user)
                .teamClub(teamClub)
                .build();

        return post;
    }
}
