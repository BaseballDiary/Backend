package com.backend.baseball.Login.login.interceptor;

import com.backend.baseball.Login.login.controller.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("{} 인증 필터 동작",requestURI);

        HttpSession session = request.getSession(false);
        if(session==null||session.getAttribute(SessionConst.LOGIN_USER_INFO)==null){
            log.info("미인증 사용자 요청");

            request.getRequestDispatcher("/api/error").forward(request, response);

            return false;
        }

        return true;
    }
}
