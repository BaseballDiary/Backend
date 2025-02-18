package com.backend.baseball.Community.service;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.dto.PostRequestDto;
import com.backend.baseball.Community.entity.dto.PostResponseDto;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.backend.baseball.User.entity.User;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    /*CREATE*/
    @Transactional
    public Long save(PostRequestDto postRequestDto,String nickname,String teamClub){
        User user= userRepository.findByNickname(nickname);

        if(!user.getMyClub().equals(teamClub)){
            throw new IllegalArgumentException("해당 구단에서만 게시글을 작성할 수 있습니다.");
        }
        postRequestDto.setUser(user);
        postRequestDto.setTeamClub(teamClub);

        Post post=postRequestDto.toEntity();
        postRepository.save(post);

        return post.getPostCertificateId();
    }

    /*READ*/
    @Transactional(readOnly = true)
    public PostResponseDto getByPostCertificateId(Long postCertificateId){
        Post post=postRepository.findByPostCertificateId(postCertificateId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id: "+postCertificateId));
        return PostResponseDto.from(post);
    }

    // 전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPosts(String teamClub,Pageable pageable){
        if("KBO".equalsIgnoreCase(teamClub)){
            return postRepository.findAll(pageable).map(PostResponseDto::from);
        }
        return postRepository.findByTeamClub(teamClub,pageable).map(PostResponseDto::from);
    }

    // 인기 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPopularPosts(String teamClub,Pageable pageable){
        if("KBO".equalsIgnoreCase(teamClub)){
            return postRepository.findByLikesGreaterThanEqual(10,pageable).map(PostResponseDto::from);
        }
        return postRepository.findByLikesGreaterThanEqual(10,pageable).map(PostResponseDto::from);
    }


}
