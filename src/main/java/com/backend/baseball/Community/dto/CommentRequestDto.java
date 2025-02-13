package com.backend.baseball.Community.dto;

import com.backend.baseball.Community.entity.Comment;
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
public class CommentRequestDto {
    private Long commentCertificateId;
    private String commentContent;
    private User user;
    private Post post;

    public Comment toEntity(){
        Comment comment=Comment.builder()
                .commentCertificateId(commentCertificateId)
                .commentContent(commentContent)
                .user(user)
                .post(post)
                .build();
        return comment;
    }
}
