package com.backend.baseball.User.helper;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.dto.SignupRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountHelper {
    //회원가입
    void join(SignupRequestDTO joinReq);

    //로그인
    String login(LoginRequestDTO loginReq, HttpServletRequest req, HttpServletResponse res);

    //회원 이메일 조회(아이디 조회)--
    Long getMemberId(HttpServletRequest req);

    //로그인 여부 확인
    boolean isLoggedIn(HttpServletRequest req);

    //로그아웃
    void logout(HttpServletRequest req, HttpServletResponse res);
}
