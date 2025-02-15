package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.dto.CommentRequestDto;
import com.backend.baseball.Community.dto.CommentResponseDto;
import com.backend.baseball.Community.service.CommentService;
import com.backend.baseball.Login.User.controller.response.CreateUserResponse;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.login.argumentresolver.Login;
import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    /*CREATE*/
    // 특정 아이디의 게시글의 댓글을 생성
    @PostMapping("/teams/{teamClub}/posts/{postCertificateId}/comments")
    public ResponseEntity<Long> save(@PathVariable String teamClub,
                                        @PathVariable Long postCertificateId,
                                     @RequestBody CommentRequestDto commentRequestDto,
                                     @Login User user/*어떤 걸 써야 하나*/) {
        return ResponseEntity.ok(commentService.save(postCertificateId,user.getNickname(),commentRequestDto));
    }

    /*CREATE*/
    @PostMapping("/posts/{postCertificateId}/comments")
    public ResponseEntity<Long> save(@PathVariable Long postCertificateId,
                                     @RequestBody CommentRequestDto commentRequestDto,
                                     @Login User user){
        return ResponseEntity.ok(commentService.save(postCertificateId,user.getNickname(),commentRequestDto));
    }

    /*READ*/
    // 특정 아이디를 가진 게시글의 댓글을 모두 조회
    @GetMapping("/teams/{teamClub}/posts/{postCertificateId}/comments")
    public List<CommentResponseDto> read(@PathVariable Long postCertificateId, @PathVariable String teamClub) {
        return commentService.findAll(postCertificateId);
    }

    /*READ*/
    // 특정 아이디를 가진 게시글의 댓글을 모두 조회
    @GetMapping("/posts/{postCertificateId}/comments")
    public List<CommentResponseDto> read(@PathVariable Long postCertificateId) {
        return commentService.findAll(postCertificateId);
    }

    /*UPDATE*/
    @PutMapping({"/teams/{teamClub}/posts/{postCertificateId}/comments/{commentCertificateId}"})
    public ResponseEntity<Long> update(@PathVariable Long postCertificateId, @PathVariable Long commentCertificateId, @RequestBody CommentRequestDto commentRequestDto, @PathVariable String teamClub) {
        commentService.update(postCertificateId,commentCertificateId,commentRequestDto);
        return ResponseEntity.ok(commentCertificateId);
    }

    /*UPDATE*/
    @PutMapping({"/posts/{postCertificateId}/comments/{commentCertificateId}"})
    public ResponseEntity<Long> update(@PathVariable Long postCertificateId, @PathVariable Long commentCertificateId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.update(postCertificateId,commentCertificateId,commentRequestDto);
        return ResponseEntity.ok(commentCertificateId);
    }

    /*DELETE*/
    @DeleteMapping("/teams/{teamClub}/posts/{postCertificateId}/comments/{commentCertificateId}")
    public ResponseEntity<Long> delete(@PathVariable Long postCertificateId, @PathVariable Long commentCertificateId, @PathVariable String teamClub) {
        commentService.delete(postCertificateId,commentCertificateId);
        return ResponseEntity.ok(commentCertificateId);
    }

    /*DELETE*/
    @DeleteMapping("/posts/{postCertificateId}/comments/{commentCertificateId}")
    public ResponseEntity<Long> delete(@PathVariable Long postCertificateId, @PathVariable Long commentCertificateId) {
        commentService.delete(postCertificateId,commentCertificateId);
        return ResponseEntity.ok(commentCertificateId);
    }
}
