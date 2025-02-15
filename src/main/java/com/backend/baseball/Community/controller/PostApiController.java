package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.dto.PostRequestDto;
import com.backend.baseball.Community.dto.PostResponseDto;
import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.service.PostService;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.login.argumentresolver.Login;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping() // 공통 URL 적용
@RequiredArgsConstructor
public class PostApiController {
//""
    private final PostService postService;


    /*CREATE */
    @PostMapping("/posts")
    public ResponseEntity save(@RequestBody PostRequestDto postRequestDto,@Login User user) {
        return ResponseEntity.ok(postService.save(postRequestDto,user.getNickname()));
    }

    /** CREATE - 게시글 생성 */
    @PostMapping("/teams/{teamClub}/posts")
    public ResponseEntity<Long> save(@PathVariable String teamClub,@RequestBody PostRequestDto postRequestDto, @Login User user) {
        postRequestDto.setTeamClub(teamClub); // ✅ 팀 정보 설정 추가
        return ResponseEntity.ok(postService.save(postRequestDto, user.getNickname()));
    }

    /**READ - 단일 게시글 조회 */
    @GetMapping("/teams/{teamClub}/posts/{postCertificateId}")
    public ResponseEntity<PostResponseDto> read(@PathVariable String teamClub,@PathVariable Long postCertificateId) {
        postService.updateView(postCertificateId);
        return ResponseEntity.ok(postService.findByPostCertificateId(postCertificateId));
    }

    /*READ 단일 게시글 조회*/
    @GetMapping("/posts/{postCertificateId}")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto postRequestDto,@PathVariable Long postCertificateId) {
        postService.updateView(postCertificateId);
        return ResponseEntity.ok(postService.findByPostCertificateId(postCertificateId));
    }

    /**READ - 게시글 목록 조회 (페이징) */
    @GetMapping("/teams/{teamClub}/posts")
    public ResponseEntity<Page<PostResponseDto>> list(
            @PathVariable String teamClub,
            @PageableDefault(size = 10, sort = "postCertificateId", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.ok(postService.findByTeamClub(teamClub, pageable));
    }

    /**UPDATE - 게시글 수정 */
    @PutMapping("/teams/{teamClub}/posts/{postCertificateId}")
    public ResponseEntity update(@PathVariable String teamClub,
                                        @PathVariable Long postCertificateId,
                                       @RequestBody PostRequestDto postRequestDto,
                                       @Login User user) {
        postService.update(postCertificateId, postRequestDto, user);
        return ResponseEntity.ok().build();
    }

    /*UPDATE 게시글 수정*/
    public ResponseEntity update(@PathVariable Long postCertificateId,@RequestBody PostRequestDto postRequestDto,@Login User user) {
        postService.update(postCertificateId,postRequestDto,user);
        return ResponseEntity.ok().build();
    }

    /**DELETE - 게시글 삭제 */
    @DeleteMapping("/teams/{teamClub}/posts/{postCertificateId}")
    public ResponseEntity<Void> delete(@PathVariable String teamClub,@PathVariable Long postCertificateId) {
        postService.delete(postCertificateId);
        return ResponseEntity.ok().build();
    }

    /*DELETE*/
    @DeleteMapping("/posts/{postCertificateId}")
    public ResponseEntity delete(@PathVariable Long postCertificateId) {
        postService.delete(postCertificateId);
        return ResponseEntity.ok().build();
    }
    /*

    @PatchMapping("/{postCertificateId}/views")
    public ResponseEntity<Void> updateView(@PathVariable Long postCertificateId) {
        postService.updateView(postCertificateId);
        return ResponseEntity.ok().build();
    }
    */

    /**검색 (키워드 기반) */
    @GetMapping("/teams/{teamClub}/posts/search")
    public ResponseEntity<Page<PostResponseDto>> search(@PathVariable String teamClub,@RequestParam String keyword, Pageable pageable) {
        return ResponseEntity.ok(postService.searchByTeamClub(teamClub, keyword, pageable));
    }

    /** 📌 구단별 인기 게시글 조회 API */
    @GetMapping("/teams/{teamClub}/posts/popular")
    public ResponseEntity<List<PostResponseDto>> getPopularPostsByTeam(@PathVariable String teamClub) {
        return ResponseEntity.ok(postService.getPopularPostsByTeam(teamClub));
    }
}
