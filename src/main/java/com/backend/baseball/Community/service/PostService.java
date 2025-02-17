package com.backend.baseball.Community.service;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.TeamCategory;
import com.backend.baseball.Community.entity.dto.PostRequestDto;
import com.backend.baseball.Community.entity.dto.PostResponseDto;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.Community.repository.TeamCategoryRepository;
import com.backend.baseball.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.backend.baseball.User.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TeamCategoryRepository teamCategoryRepository;

    /*CREATE*/
    @Transactional
    public Long save(PostRequestDto postRequestDto,String nickname,String teamCategoryTitle){
        User user= userRepository.findByNickname(nickname);
        TeamCategory teamCategory = teamCategoryRepository.findByTeamCategoryTitle(teamCategoryTitle)
                .orElseGet(() -> teamCategoryRepository.save(new TeamCategory(teamCategoryTitle)));

        postRequestDto.setUser(user);
        postRequestDto.setTeamCategory(teamCategory);

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
    public Page<PostResponseDto> getAllPosts(String teamCategoryTitle,Pageable pageable){
        if("KBO".equalsIgnoreCase(teamCategoryTitle)){
            return postRepository.findAll(pageable).map(PostResponseDto::from);
        }

        TeamCategory teamCategory=teamCategoryRepository.findByTeamCategoryTitle(teamCategoryTitle)
                .orElseThrow(()-> new IllegalArgumentException("해당 구단이 존재하지 않습니다. "+teamCategoryTitle));
        return postRepository.findByTeamCategory(teamCategory,pageable).map(PostResponseDto::from);
    }

    // 인기 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPopularPosts(String teamCategoryTitle,Pageable pageable){
        if("KBO".equalsIgnoreCase(teamCategoryTitle)){
            return postRepository.findByLikesGreaterThanEqual(10,pageable).map(PostResponseDto::from);
        }

        TeamCategory teamCategory=teamCategoryRepository.findByTeamCategoryTitle(teamCategoryTitle)
                .orElseThrow(()->new IllegalArgumentException("해당 구단이 존재하지 않습니다. "+teamCategoryTitle));
        return postRepository.findByTeamCategoryAndLikesGreaterThanEqual(teamCategory,10,pageable).map(PostResponseDto::from);
    }


}
