package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.Post;
import com.backend.baseball.Community.entity.dto.CommentRequestDto;
import com.backend.baseball.Community.entity.dto.CommentResponseDto;
import com.backend.baseball.Community.repository.PostRepository;
import com.backend.baseball.Community.service.CommentService;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.baseball.User.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /* 댓글 생성 */
    @PostMapping("/posts/{postCertificateId}/comments/create")
    public ResponseEntity<?> save(@PathVariable Long postCertificateId,
                                  @RequestBody CommentRequestDto commentRequestDto,
                                  HttpServletRequest req) {

        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }

        User user = userOptional.get();

        Optional<Post> postOptional = postRepository.findByPostCertificateId(postCertificateId);
        if (postOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "해당 게시글을 찾을 수 없습니다."));
        }

        Post post = postOptional.get();

        if (!user.getMyClub().equals(post.getTeamClub())) {
            return ResponseEntity.status(403).body(Map.of("error", "해당 구단의 게시글에만 댓글을 작성할 수 있습니다."));
        }

        Long commentCertificateId = commentService.save(postCertificateId, user.getNickname(), commentRequestDto);
        return ResponseEntity.ok(Map.of("message", "댓글이 성공적으로 생성되었습니다.", "commentId", commentCertificateId));
    }

    /* 댓글 조회 */
    @GetMapping("/posts/{postCertificateId}/comments")
    public ResponseEntity<List<CommentResponseDto>> read(@PathVariable Long postCertificateId) {
        return ResponseEntity.ok(commentService.findAll(postCertificateId));
    }


}
