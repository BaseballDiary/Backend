package com.backend.baseball.Community.entity.dto;

import com.backend.baseball.Community.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final Long postCertificateId;
    private final String contents;
    private final int likes;
    private final Long userCertificateId;
    private final List<CommentResponseDto> comments;
    private final String teamClub;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postCertificateId(post.getPostCertificateId())
                .contents(post.getContents())
                .likes(post.getLikes())
                .teamClub(post.getTeamClub())
                .userCertificateId(post.getUser().getCertificateId())
                .comments(post.getComments() !=null?
                        post.getComments().stream()
                                .map(CommentResponseDto::from)
                                .collect(Collectors.toList())
                :Collections.emptyList())
                .build();
    }

}
