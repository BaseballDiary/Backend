package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.dto.SignupRequestDTO;
import com.backend.baseball.User.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor


public class AccountController {

    private final AccountHelper accountHelper;

    @PostMapping("/user")
    public ResponseEntity<?> join(@RequestBody SignupRequestDTO joinReq){
        //입력 값이 비어 있다면
        if(!StringUtils.hasLength(joinReq.getEmail()) || !StringUtils.hasLength(joinReq.getPassword())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        accountHelper.join(joinReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<?> check(HttpServletRequest req) {
        return new ResponseEntity<>(accountHelper.isLoggedIn(req), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest req, HttpServletResponse res){
        accountHelper.logout(req, res);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
