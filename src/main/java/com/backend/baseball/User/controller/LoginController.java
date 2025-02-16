package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.LoginRequestDTO;
import com.backend.baseball.User.helper.AccountHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller  // ✅ @RestController가 아니라 @Controller라면, @ResponseBody 필요
@RequestMapping("/login")
public class LoginController {

    private final AccountHelper accountHelper;

    @PostMapping
    @ResponseBody  // ✅ JSON 응답을 반환하기 위해 필요
    public ResponseEntity<?> login(HttpServletRequest req, HttpServletResponse res, @RequestBody LoginRequestDTO loginReq) {
        // 입력 값이 비어 있다면
        if (!StringUtils.hasLength(loginReq.getEmail()) || !StringUtils.hasLength(loginReq.getPassword())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String output = accountHelper.login(loginReq, req, res);

        if (output == null) { // 로그인 실패 시
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
