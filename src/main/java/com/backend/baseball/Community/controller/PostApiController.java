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

    /*CREATE*/
    @PostMapping("/posts/create")
    public ResponseEntity save(@RequestBody PostRequestDto postRequestDto, User user,@RequestParam String teamCategoryTitle) {
        Long postCertificateId=postService.save(postRequestDto,user.getNickname(),teamCategoryTitle);
        return ResponseEntity.ok(postCertificateId);
    }

    /*READ - 단일 게시글 조회*/
    @GetMapping("/posts/read/{postCertificateId}")
    public ResponseEntity read(@PathVariable Long postCertificateId,@RequestParam String teamCategoryTitle) {

        return ResponseEntity.ok(postService.findByPostCertificateId(postCertificateId));
    }

    /*전체 게시글 조회*/
    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponseDto>> getPostsByTeamCategory(
            @RequestParam(required = false,defaultValue = "KBO") String teamCategoryTitle
            , Pageable pageable){
        return ResponseEntity.ok(postService.pageList(teamCategoryTitle,pageable));
    }





}
