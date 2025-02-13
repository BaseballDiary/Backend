package com.backend.baseball.Login.login.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collection;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
public class CorsCookieInterceptor implements HandlerInterceptor {
    private final String SESSION_COOKIE_NAME = "JSESSIONID";
    private final String SAME_SITE_ATTRIBUTE_VALUES = "; HttpOnly; Secure; SameSite=None";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            Collection<String> headers = response.getHeaders(SET_COOKIE);

            if (headers != null && !headers.isEmpty()) {
                log.info("Set-Cookie 헤더 감지됨. 개수: {}", headers.size());

                // ✅ 기존 쿠키 값을 제거하고 다시 추가
                response.setHeader(SET_COOKIE, null);

                for (String header : headers) {
                    log.info("Set-Cookie 원본 값: {}", header);

                    if (header.startsWith(SESSION_COOKIE_NAME)) {
                        log.info("세션 쿠키 속성 설정 중...");
                        String updatedHeader = header + SAME_SITE_ATTRIBUTE_VALUES;

                        try {
                            response.addHeader(SET_COOKIE, updatedHeader);
                            log.info("업데이트된 쿠키 설정: {}", updatedHeader);
                        } catch (Exception e) {
                            log.error("쿠키 설정 중 오류 발생: {}", e.getMessage(), e);
                        }
                    } else {
                        response.addHeader(SET_COOKIE, header);
                    }
                }
            } else {
                log.warn("Set-Cookie 헤더가 존재하지 않음.");
            }
        } catch (Exception e) {
            log.error("쿠키 설정 과정에서 예외 발생: {}", e.getMessage(), e);
        }
    }
}