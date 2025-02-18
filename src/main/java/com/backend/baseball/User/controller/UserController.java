package com.backend.baseball.User.controller;

import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import com.backend.baseball.User.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import com.backend.baseball.User.entity.User;

@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final AccountHelper accountHelper;

    @PostMapping("/setMyClub")
    public ResponseEntity<?> setMyClub(@RequestParam String myClub,HttpServletRequest req) {
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }

        User user = userOptional.get();
        user.changeClub(myClub);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "구단이 성공적으로 설정되었습니다."));
    }

    @PostMapping("/setNickname")
    public ResponseEntity<?> setNickname(@RequestParam String nickname, HttpServletRequest req) {
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "로그인이 필요합니다."));
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "사용자를 찾을 수 없습니다."));
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(nickname)) {
            return ResponseEntity.status(400).body(Map.of("error", "이미 존재하는 닉네임입니다."));
        }

        User user = userOptional.get();
        user.changeNickname(nickname);
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "닉네임이 성공적으로 변경되었습니다."));
    }

}
