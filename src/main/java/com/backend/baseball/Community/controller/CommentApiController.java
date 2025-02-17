package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.dto.CommentRequestDto;
import com.backend.baseball.Community.entity.dto.CommentResponseDto;
import com.backend.baseball.Community.service.CommentService;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.baseball.User.entity.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;
    private final AccountHelper accountHelper;
    private final UserRepository userRepository;

    /*CREATE (내 구단에서만 댓글 작성 가능) */
    @PostMapping("/posts/{postCertificateId}/comments/create")
    public ResponseEntity<?> save(@PathVariable Long postCertificateId,
                                  @RequestBody CommentRequestDto commentRequestDto,
                                  HttpServletRequest request) {

        // 현재 로그인한 사용자 정보 가져오기
        Long memberId = accountHelper.getMemberId(request);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        // 현재 로그인한 사용자의 myClub 조회
        User user = userRepository.findByNickname(commentRequestDto.getUser().getNickname());
        if (user == null) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        // 사용자의 myClub과 teamCategoryTitle이 다르면 댓글 작성 불가
        if (!user.getMyClub().equals(commentRequestDto.getPost().getTeamCategory().getTeamCategoryTitle())) {
            return ResponseEntity.status(403).body("{\"status\": 403, \"message\": \"본인의 구단에서만 댓글을 작성할 수 있습니다.\"}");
        }

        Long commentCertificateId = commentService.save(postCertificateId, user.getNickname(), commentRequestDto);
        return ResponseEntity.ok(commentCertificateId);
    }

    /*READ*/
    @GetMapping("/posts/{postCertificateId}/comments")
    public List<CommentResponseDto> read(@PathVariable Long postCertificateId) {
        return commentService.findAll(postCertificateId);
    }



}
