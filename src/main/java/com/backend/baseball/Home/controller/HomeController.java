package com.backend.baseball.Home.controller;
import com.backend.baseball.Home.dto.AttendanceResponseDTO;
import com.backend.baseball.Home.dto.UserTemperatureDTO;
import com.backend.baseball.Home.service.AttendanceService;
import com.backend.baseball.Login.User.repository.UserRepository;
import com.backend.baseball.Login.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {
    private final UserRepository userRepository;
    private final AttendanceService attendanceService;

    @GetMapping
    public String home() {
        return "Welcome to Baseball Diary API!";
    }

    //출석 정보 제공 API
    @PostMapping("/attendance")
    public ResponseEntity<AttendanceResponseDTO> getAttendanceData(
            @RequestBody Map<String, Integer> request, // { "year": 2024, "month": 1 }
            HttpSession session) {

        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        int year = request.get("year");
        int month = request.get("month");

        AttendanceResponseDTO attendanceData = attendanceService.getAttendanceData(year, month, certificatedId);
        return ResponseEntity.ok(attendanceData);
    }

    //홈 화면에서 사용자 닉네임 & 온도 가져오기
    @GetMapping("/name")
    public UserTemperatureDTO getUserInfo(HttpSession session) {
        // 세션에서 로그인한 사용자의 certificateId 가져오기
        Long certificatedId = (Long) session.getAttribute("certificated_id");

        if (certificatedId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        // User 엔티티에서 사용자 정보 가져오기
        User user = userRepository.findById(certificatedId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        // 현재 날짜 기준 출석률 계산 (반올림 적용됨)
        LocalDate today = LocalDate.now();
        int temperature = attendanceService.calculateTemperature(certificatedId, today.getYear(), today.getMonthValue());

        return new UserTemperatureDTO(user.getNickname(), temperature);
    }
}