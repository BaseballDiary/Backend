package com.backend.baseball.Community.service;

import com.backend.baseball.Community.entity.Comment;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.dto.CommentRequestDto;
import com.backend.baseball.Community.entity.dto.CommentResponseDto;
import com.backend.baseball.Community.repository.CommentRepository;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backend.baseball.User.entity.User;

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
    public Long save(Long postCertificateId, String nickname, CommentRequestDto commentRequestDto) {
        User user=userRepository.findByNickname(nickname);
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalStateException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다. "+postCertificateId));

        // 사용자의 myClub과 게시글의 teamClub이 동일한지 확인
        if(!user.getMyClub().equals(post.getTeamClub())){
            throw new IllegalStateException("해당 구단의 게시글에만 댓글을 작성할 수 있습니다.");
        }

        commentRequestDto.setUser(user);
        commentRequestDto.setPost(post);
        Comment comment=commentRequestDto.toEntity();
        commentRepository.save(comment);
        return comment.getCommentCertificateId();
    }

    /*READ*/
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long postCertificateId) {
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. "+postCertificateId));
        List<Comment> comments=post.getComments();
        return comments.stream().map(CommentResponseDto::from).collect(Collectors.toList());
    }



}
