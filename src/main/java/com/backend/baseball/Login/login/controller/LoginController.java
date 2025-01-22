package com.backend.baseball.Login.login.controller;

import com.backend.baseball.Login.login.controller.dto.LoginInfo;
import com.backend.baseball.Login.login.controller.dto.LoginRequestDto;
import com.backend.baseball.Login.login.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> Login(
            HttpServletRequest request,
            @RequestBody LoginRequestDto loginRequestDto){
        LoginInfo loginInfo=loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        loginService.makeLoginSession(loginInfo,request);

        return ResponseEntity.ok().body("login success");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> Logout(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        if(session!=null){
            System.out.println("logout");
            session.invalidate();
        }
        return ResponseEntity.ok().body("logout success");
    }

}
