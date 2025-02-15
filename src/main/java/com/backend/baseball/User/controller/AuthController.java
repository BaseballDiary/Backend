package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.AddUserRequest;
import com.backend.baseball.User.dto.MailDTO;
import com.backend.baseball.User.service.MailService;
import com.backend.baseball.User.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final MailService mailService;

    //인증번호 저장(이메일별로 저장)
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    //인증번호 요청
    @ResponseBody
    @PostMapping
    public String emailCheck(@RequestBody MailDTO mailDTO) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendSimpleMessage(mailDTO.getEmail());
        verificationCodes.put(mailDTO.getEmail(), authCode); // 이메일별 인증번호 저장
        return "인증번호가 전송되었습니다."; // 인증번호 반환 X (보안 문제)
    }

    // 인증번호 확인
    @ResponseBody
    @PostMapping("/confirm")
    public Map<String, Object> confirmAuthCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String inputCode = request.get("code");

        Map<String, Object> response = new HashMap<>();

        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(inputCode)) {
            response.put("status", "success");
            response.put("message", "인증이 완료되었습니다.");
            verificationCodes.remove(email); // 인증 성공 시 삭제
        } else {
            response.put("status", "error");
            response.put("message", "인증번호가 일치하지 않습니다.");
        }
        return response;
    }
}

