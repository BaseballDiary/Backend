package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.MyClubResponseDTO;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/diary")  // ✅ 엔드포인트 기본 URL 설정
@RequiredArgsConstructor
public class FetchMyClubController {

    private final AccountHelper accountHelper;
    private final UserRepository userRepository;

    @GetMapping("/fetchMyClub")
    public ResponseEntity<?> fetchMyClub(HttpServletRequest req) {
        // 현재 로그인한 사용자의 memberId 가져오기
        Long memberId = accountHelper.getMemberId(req);

        // 로그인되지 않은 경우
        if (memberId == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findById(memberId.longValue());

        // 사용자 정보가 없을 경우
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }

        User user = userOptional.get();

        // 사용자의 myClub 정보 반환
        return ResponseEntity.ok(new MyClubResponseDTO(user.getMyClub()));
    }
}

