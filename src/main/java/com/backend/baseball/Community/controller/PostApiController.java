package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.dto.PostRequestDto;
import com.backend.baseball.Community.entity.dto.PostResponseDto;
import com.backend.baseball.Community.service.PostService;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;

    // 게시글 생성 (내 구단에서만 작성 가능)
    @PostMapping("/posts/create")
    public ResponseEntity<?> save(@RequestBody PostRequestDto postRequestDto,
                                  @RequestParam String teamCategoryTitle,
                                  HttpServletRequest request) {

        // 현재 로그인한 사용자 정보 가져오기
        Long memberId = accountHelper.getMemberId(request);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        // 현재 로그인한 사용자의 myClub 조회
        User user = userRepository.findByNickname(postRequestDto.getUser().getNickname());
        if (user == null) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        // 사용자의 myClub과 teamCategoryTitle이 다르면 게시글 작성 불가
        if (!user.getMyClub().equals(teamCategoryTitle)) {
            return ResponseEntity.status(403).body("{\"status\": 403, \"message\": \"본인의 구단에서만 게시글을 작성할 수 있습니다.\"}");
        }

        Long postCertificateId = postService.save(postRequestDto, user.getNickname(), teamCategoryTitle);
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
            @RequestParam(required = false, defaultValue = "KBO") String teamCategoryTitle,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(teamCategoryTitle, pageable));
    }

    // 인기 게시글 조회
    @GetMapping("/popular")
    public ResponseEntity<Page<PostResponseDto>> getPopularPosts(
            @RequestParam(required = false, defaultValue = "KBO") String teamCategoryTitle,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPopularPosts(teamCategoryTitle, pageable));
    }


}
