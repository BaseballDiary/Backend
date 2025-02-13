package com.backend.baseball.Community.controller;

import com.backend.baseball.Community.dto.LikesRequestDto;
import com.backend.baseball.Community.service.LikesService;
import com.backend.baseball.Login.entity.User;
import com.backend.baseball.Login.login.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes") // 공통 URL 설정
@RequiredArgsConstructor
public class LikesApiController {

    private final LikesService likesService;

    /** 📌 좋아요 추가 */
    @PostMapping
    public ResponseEntity<Void> likePost(@RequestBody LikesRequestDto likesRequestDto, @Login User user) {
        //likesRequestDto.setCertificateId(user.getCertificateId()); // 로그인 유저의 ID 설정
        likesService.insert(likesRequestDto);
        return ResponseEntity.ok().build();
    }

    /** 📌 좋아요 취소 */
    @DeleteMapping
    public ResponseEntity<Void> unlikePost(@RequestBody LikesRequestDto likesRequestDto, @Login User user) {
        //likesRequestDto.setCertificateId(user.getCertificateId()); // 로그인 유저의 ID 설정
        likesService.delete(likesRequestDto);
        return ResponseEntity.ok().build();
    }
}
