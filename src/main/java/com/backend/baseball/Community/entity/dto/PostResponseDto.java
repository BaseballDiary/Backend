package com.backend.baseball.Community.entity.dto;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.TeamCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final Long postCertificateId;
    private final String title;
    private final String contents;
    private final int views;
    private final TeamCategory teamCategory;
    private final Long userCertificateId;
    private final List<CommentResponseDto> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postCertificateId(post.getPostCertificateId())
                .title(post.getTitle())
                .contents(post.getContents())
                .views(post.getViews())
                .teamCategory(post.getTeamCategory())
                .userCertificateId(post.getUser().getCertificateId())
                .comments(post.getComments() !=null?
                        post.getComments().stream()
                                .map(CommentResponseDto::from)
                                .collect(Collectors.toList())
                :Collections.emptyList())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

}
