package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.entity.dto.CommentRequestDto;
import com.backend.baseball.Community.entity.dto.CommentResponseDto;
import com.backend.baseball.Community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.baseball.User.entity.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    /*CREATE*/
    @PostMapping("/posts/{postCertificateId}/comments")
    public ResponseEntity<Long> save(@PathVariable Long postCertificateId
                                    ,@RequestBody CommentRequestDto commentRequestDto
                                    ,User user){
        return ResponseEntity.ok(commentService.save(postCertificateId,user.getNickname(),commentRequestDto));
    }

    /*READ*/
    @GetMapping("/posts/{postCertificateId}/comments")
    public List<CommentResponseDto> read(@PathVariable Long postCertificateId){
        return commentService.findAll(postCertificateId);
    }



}
