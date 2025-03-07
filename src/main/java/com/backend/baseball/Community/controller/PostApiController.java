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

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostApiController {

    private final PostService postService;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;

    /* 게시글 생성 */
    @PostMapping("/posts/create")
    public ResponseEntity<?> save(@RequestBody PostRequestDto postRequestDto,
                                  HttpServletRequest req,
                                  @RequestParam String teamClub) {

        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }

        User user = userOptional.get();

        if (!user.getMyClub().equals(teamClub)) {
            return ResponseEntity.status(403).body(Map.of("error", "해당 구단에서만 게시글을 작성할 수 있습니다."));
        }

        Long postCertificateId = postService.save(postRequestDto, user.getNickname(), teamClub);
        return ResponseEntity.ok(Map.of("message", "게시글이 성공적으로 생성되었습니다.", "postId", postCertificateId));
    }

    /* 단일 게시글 조회 */
    @GetMapping("/posts/read/{postCertificateId}")
    public ResponseEntity<PostResponseDto> getPostByPostCertificateId(@PathVariable Long postCertificateId) {
        return ResponseEntity.ok(postService.getByPostCertificateId(postCertificateId));
    }

    /* 전체 게시글 조회 */
    @GetMapping("/all")
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @RequestParam(required = false, defaultValue = "KBO") String teamClub,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(teamClub, pageable));
    }

    /* 인기 게시글 조회 */
    @GetMapping("/popular")
    public ResponseEntity<Page<PostResponseDto>> getPopularPosts(
            @RequestParam(required = false, defaultValue = "KBO") String teamClub,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getPopularPosts(teamClub, pageable));
    }

}
