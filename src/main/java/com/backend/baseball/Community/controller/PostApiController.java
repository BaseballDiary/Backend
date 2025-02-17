package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.dto.PostRequestDto;
import com.backend.baseball.Community.entity.dto.PostResponseDto;
import com.backend.baseball.Community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import com.backend.baseball.User.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostApiController {

    private final PostService postService;

    // 게시글 생성
    @PostMapping("/posts/create")
    public ResponseEntity save(@RequestBody PostRequestDto postRequestDto, User user,@RequestParam String teamCategoryTitle) {
        Long postCertificateId=postService.save(postRequestDto,user.getNickname(),teamCategoryTitle);
        return ResponseEntity.ok(postCertificateId);
    }

    // 단일 게시글 조회
    @GetMapping("posts/read/{postCertificateId}")
    public ResponseEntity<PostResponseDto> getPostByPostCertificateId(@PathVariable Long postCertificateId) {
        return ResponseEntity.ok(postService.getByPostCertificateId(postCertificateId));
    }

    // 전체 게시글 조회
    @GetMapping("/all")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam(required = false,defaultValue = "KBO") String teamCategoryTitle,
            Pageable pageable){
        return ResponseEntity.ok(postService.getAllPosts(teamCategoryTitle,pageable));
    }

    // 인기 게시글 조회
    @GetMapping("/popular")
    public ResponseEntity<Page<PostResponseDto>> getPopularPosts(
            @RequestParam(required = false,defaultValue = "KBO") String teamCategoryTitle,
            Pageable pageable){
        return ResponseEntity.ok(postService.getPopularPosts(teamCategoryTitle,pageable));
    }





}
