package com.backend.baseball.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpUtils {
    //세션 입력 - 세션 데이터를 입력하는 메서드. 매개변수로 HTTP 요청 객체와 세션의 키, 그리고 세션의 값을 받는다.
    // 이후 로그인에 성공한 사용자의 정보 저장 시 사용
    public static void setSession(HttpServletRequest req, String key, Object value){
        req.getSession().setAttribute(key, value);
    }

    //세션 값 조회 - HTTP 세션의 특정 값을 조회하는 메서드. 매개변수로 HTTP 요청 객체와 세션의 키를 받는다.
    //이후에 로그인 사용자의 회원의 아이디를 조회하는 목적으로 사용될 것
    public static Object getSessionValue(HttpServletRequest req, String key){
        return req.getSession().getAttribute(key);
    }

    //세션 삭제 - HTTP 세션의 값을 삭제하는 메서드. 매개변수로 HTTP 요청 객체를 받는다.
    //이후 로그아웃을 요청한 사용자의 세션을 삭제하는 목적으로 사용
    public static void removeSession(HttpServletRequest req, String key){
        req.getSession().removeAttribute(key);
    }
}
