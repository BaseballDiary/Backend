package com.backend.baseball.Login.login.controller;

import com.backend.baseball.Login.login.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InterceptorErrorController {
    @RequestMapping("/api/error")
    public void error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnauthorizedException();
    }

}
