package com.backend.baseball.User.service;


import com.backend.baseball.User.dto.LoginResponseDTO;
import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequest, HttpServletRequest request, HttpServletResponse response) {
        // 1. 사용자 조회
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. SecurityContext에 인증 정보 저장
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 4. 세션에 SecurityContext 저장
        HttpSession session = request.getSession(true); // 세션이 없으면 생성
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        session.setAttribute("loginUser", user); // ✅ 로그인한 사용자 정보도 세션에 저장

        // 5. JSESSIONID 쿠키 설정 (SameSite=None, Secure 설정)
        String jsessionid = session.getId();
        boolean isSecure = request.isSecure(); // HTTPS인지 확인
        String cookieHeader = "JSESSIONID=" + jsessionid + "; Path=/; HttpOnly; SameSite=None" + (isSecure ? "; Secure" : "");
        response.setHeader("Set-Cookie", cookieHeader);

        // 6. 로그인 성공 응답 반환
        return new LoginResponseDTO(user.getEmail(), "로그인 성공", session.getId());

    }
    //이메일로 사용자 조회하는 메서드 추가
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
    }

}

