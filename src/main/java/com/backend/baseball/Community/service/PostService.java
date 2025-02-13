package com.backend.baseball.Community.service;

import com.backend.baseball.Community.dto.PostRequestDto;
import com.backend.baseball.Community.dto.PostResponseDto;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /*CREATE*/
    // 처음에 게시글을 생성할 때는 User가 만드는 거니까 User 만 필요함, postRequestDto랑
    @Transactional
    public Long save(PostRequestDto postRequestDto,String nickname) {
        /*User 정보를 가져와 dto에 담아준다.*/
        User user= userRepository.findByNickname(nickname);
        postRequestDto.setUser(user);
        log.info("PostService save()실행");
        Post post=postRequestDto.toEntity();
        postRepository.save(post);
        return post.getPostCertificateId();
    }

    /*READ 게시글 리스트 조회 readOnly 속성으로 조회속도 개선*/
    // 이거는 이미 만들어진 걸 조회하는 거니까 PostResponseDto이다.
    @Transactional(readOnly = true)
    public PostResponseDto findByPostCertificateId(Long postCertificateId) {
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. 게시글 id: "+postCertificateId));
        return PostResponseDto.from(post);
    }

    /*UPDATE (dirty checking 영속성 컨텍스트)
    * User 객체를 영속화시키고, 영속화된 User 객체를 가져와 데이터를 변경하면
    * 트랜잭션이 끝날 때 자동으로 DB에 저장해준다.*/
    // 이것도 update니까
    @Transactional
    public void update(Long postCertificateId, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findByPostCertificateId(postCertificateId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: " + postCertificateId));

        // 작성자만 수정할 수 있도록 검증
        if (!post.getUser().getCertificateId().equals(user.getCertificateId())) {
            throw new SecurityException("게시글을 수정할 권한이 없습니다.");
        }

        post.update(postRequestDto.getTitle(), postRequestDto.getContents());
    }


    /*DELETE*/
    @Transactional
    public void delete(Long postCertificateId){
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: "+postCertificateId));
        postRepository.delete(post);
    }

    /*조회수*/
    @Transactional
    public int updateView(Long postCertificateId){
        return postRepository.updateView(postCertificateId);
    }

    /*페이징과 분류 - Post → PostResponseDto 변환 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> pageList(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(PostResponseDto::from); // 엔티티 → DTO 변환
    }

    /* 검색 - Post → PostResponseDto 변환 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> search(String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContaining(keyword, pageable);
        return posts.map(PostResponseDto::from); // 엔티티 → DTO 변환
    }

    /* 📌 구단별 게시글 조회 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> findByTeamClub(String teamClub, Pageable pageable) {
        Page<Post> posts = postRepository.findByTeamClub(teamClub, pageable);
        return posts.map(PostResponseDto::from); // 엔티티 → DTO 변환
    }

    /** 검색 - 특정 구단 내에서 키워드 검색 */
    @Transactional(readOnly = true)
    public Page<PostResponseDto> searchByTeamClub(String teamClub, String keyword, Pageable pageable) {
        Page<Post> posts = postRepository.findByTeamClubAndTitleContaining(teamClub, keyword, pageable);
        return posts.map(PostResponseDto::from);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getPopularPostsByTeam(String teamClub) {
        LocalDateTime timeLimit = LocalDateTime.now().minusHours(48); // 48시간 전 시간 계산
        List<Post> popularPosts = postRepository.findPopularPostsByTeam(timeLimit, teamClub);
        return popularPosts.stream().map(PostResponseDto::from).collect(Collectors.toList());
    }
}
