package com.backend.baseball.Community.repository;

import com.backend.baseball.Community.entity.Comment;
import com.backend.baseball.Community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    /*게시글 댓글 목록 불러오기*/
    List<Comment> getCommentByPostOrderByCommentCertificateId(Post post);

    Optional<Comment> findByPost_PostCertificateIdAndCommentCertificateId(Long postCertificateId,Long commentCertificateId);
}
