package com.backend.baseball.User.controller;

import com.backend.baseball.User.dto.AddUserRequest;
import com.backend.baseball.User.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class SignupController {
    private final UserService userService;
    @PostMapping("/user")
    public String signup(@Valid AddUserRequest request, BindingResult result, RedirectAttributes redirectAttributes) {
        // 입력값 유효성 검사 실패 시
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "입력값을 확인해주세요.");
            return "redirect:/signup"; // 다시 회원가입 페이지로 이동
        }

        // 비밀번호 일치 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            redirectAttributes.addFlashAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "redirect:/signup";
        }

        // 회원 저장
        userService.save(request);
        return "redirect:/mypage";
    }
}
