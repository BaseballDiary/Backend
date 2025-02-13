package com.backend.baseball.Community.service;

import com.backend.baseball.Community.dto.CommentRequestDto;
import com.backend.baseball.Community.dto.CommentResponseDto;
import com.backend.baseball.Community.entity.Comment;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.repository.CommentRepository;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /*CREATE*/
    @Transactional
    public Long save(Long postCertificateId, String nickname, CommentRequestDto commentRequestDto){
        User user=userRepository.findByNickname(nickname);
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."+postCertificateId));

        commentRequestDto.setUser(user);
        commentRequestDto.setPost(post);

        Comment comment=commentRequestDto.toEntity();
        commentRepository.save(comment);

        return comment.getCommentCertificateId();
    }

    /*READ*/
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long postCertificateId){
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다."+postCertificateId));
        List<Comment> comments=post.getComments();
        return comments.stream().map(CommentResponseDto::from).collect(Collectors.toList());
    }

    /*UPDATE*/
    @Transactional
    public void update(Long postCertificateId,Long commentCertificateId,CommentRequestDto commentRequestDto){
        Comment comment=commentRepository.findByPost_PostCertificateIdAndCommentCertificateId(postCertificateId,commentCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. "+commentCertificateId));
        comment.update(commentRequestDto.getCommentContent());
    }

    /*DELETE*/
    public void delete(Long postCertificateId,Long commentCertificateId){
        Comment comment=commentRepository.findByPost_PostCertificateIdAndCommentCertificateId(postCertificateId,commentCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다. "+commentCertificateId));
        commentRepository.delete(comment);
    }

}
