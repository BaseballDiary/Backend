package com.backend.baseball.Community.service;

import com.backend.baseball.Community.dto.LikesRequestDto;
import com.backend.baseball.Community.entity.Likes;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.repository.LikesRepository;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /** 📌 좋아요 추가 */
    @Transactional
    public void insert(LikesRequestDto likesRequestDto) {
        User user = userRepository.findByCertificateId(likesRequestDto.getCertificateId())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다. 유저의 ID: " + likesRequestDto.getCertificateId()));

        Post post = postRepository.findByPostCertificateId(likesRequestDto.getPostCertificateId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. 게시글 ID: " + likesRequestDto.getPostCertificateId()));

        // 이미 좋아요 했는지 체크
        if (likesRepository.findByUserAndPost(user, post).isPresent()) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다. 유저 ID: " + user.getCertificateId() + ", 게시글 ID: " + post.getPostCertificateId());
        }

        Likes likes = Likes.builder()
                .post(post)
                .user(user)
                .build();
        likesRepository.save(likes);

        // 📌 좋아요 수 증가
        postRepository.addLikeCount(post.getPostCertificateId());

        postRepository.flush();
    }

    /** 📌 좋아요 삭제 */
    @Transactional
    public void delete(LikesRequestDto likesRequestDto) {
        User user = userRepository.findByCertificateId(likesRequestDto.getCertificateId())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다. 유저의 ID: " + likesRequestDto.getCertificateId()));

        Post post = postRepository.findByPostCertificateId(likesRequestDto.getPostCertificateId())
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. 게시글 ID: " + likesRequestDto.getPostCertificateId()));

        Likes likes = likesRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new EntityNotFoundException("좋아요 기록을 찾을 수 없습니다."));

        likesRepository.delete(likes);

        // 📌 좋아요 수 감소
        postRepository.subLikeCount(post.getPostCertificateId());

        postRepository.flush();
    }
}
