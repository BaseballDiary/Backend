package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.dto.LoginResponseDTO;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;

    // ✅ 로그인 추가 (프론트에서 email, password를 JSON으로 보내야 함)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = httpServletRequest.getSession(false); // ✅ 기존 세션이 없으면 null 반환
        if (session == null) {
            session = httpServletRequest.getSession(true); // ✅ 필요할 때만 생성
        }

        // ✅ 로그인한 사용자 정보 가져오기
        User user = loginService.findByEmail(request.getEmail());

        // ✅ 세션에 사용자 ID와 이메일 저장
        session.setAttribute("userId", user.getCertificateId());
        session.setAttribute("loginUser", user);
        session.setAttribute("email", user.getEmail());

        log.info("로그인 성공 - 세션 ID: {}", session.getId());

        return ResponseEntity.ok(new LoginResponseDTO(request.getEmail(), "로그인 성공", session.getId()));
    }



    // ✅ 로그아웃 (세션 무효화)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
}

