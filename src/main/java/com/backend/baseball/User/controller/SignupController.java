package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.AddUserRequest;
import com.backend.baseball.User.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class SignupController {
    private final UserService userService;
    @PostMapping("/user")
    public ResponseEntity<?> signup(@Valid @RequestBody AddUserRequest request, BindingResult result) {
        // 입력값 유효성 검사 실패 시
        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력값을 확인해주세요.");
        }

        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호가 일치하지 않습니다.");
        }

        // 회원 저장
        userService.save(request);

        // ✅ 회원가입 성공 메시지 반환 (201 Created)
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
    }
}
