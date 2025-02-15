package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.dto.LoginResponseDTO;
import com.backend.baseball.User.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    // ✅ 로그인 추가 (프론트에서 email, password를 JSON으로 보내야 함)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request,
                                                  HttpServletRequest httpRequest,
                                                  HttpServletResponse httpResponse) {
        LoginResponseDTO response = loginService.login(request, httpRequest, httpResponse);
        return ResponseEntity.ok(response);
    }

    // ✅ 로그아웃 (세션 무효화)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}
