package com.backend.baseball.Community.entity.dto;

import com.backend.baseball.Community.entity.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class CommentResponseDto {
    private final Long commentCertificateId;
    private final String commentContent;
    private final String nickname;
    private final Long userCertificateId;
    private final Long postCertificateId;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    /*Entity -> Dto*/
    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentCertificateId(comment.getCommentCertificateId())
                .commentContent(comment.getCommentContent())
                .nickname(comment.getUser().getNickname())
                .userCertificateId(comment.getUser().getCertificateId())
                .postCertificateId(comment.getPost().getPostCertificateId())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }


}
