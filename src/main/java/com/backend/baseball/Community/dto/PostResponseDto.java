package com.backend.baseball.Community.dto;


import com.backend.baseball.Community.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
    private final int view;
    private final Long userCertificateId;
    private final List<CommentResponseDto> comments;
    private final String teamClub;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    /*Entity -> Dto*/
    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .postCertificateId(post.getPostCertificateId())
                .title(post.getTitle())
                .contents(post.getContents())
                .view(post.getViews())  // 조회수
                .userCertificateId(post.getUser().getCertificateId())  // 작성자 ID
                .teamClub(post.getTeamClub())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .comments(post.getComments() != null ?
                        post.getComments().stream()
                                .map(CommentResponseDto::from) // 댓글 리스트 변환
                                .collect(Collectors.toList())
                        : Collections.emptyList())  // 댓글이 없으면 빈 리스트 반환
                .build();
    }
}
