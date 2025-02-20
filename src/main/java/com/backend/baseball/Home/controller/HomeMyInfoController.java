package com.backend.baseball.Home.controller;

import com.backend.baseball.Diary.dto.CreateDiary.GameInfoResponseDTO;
import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import com.backend.baseball.Home.repository.AttendanceRepository;
import com.backend.baseball.User.entity.User;
import com.backend.baseball.User.helper.AccountHelper;
import com.backend.baseball.User.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.baseball.Home.entity.Attendance;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/home")

public class HomeMyInfoController {

    private final AccountHelper accountHelper;
    private final UserRepository userRepository;
    private final GameInfoRepository gameInfoRepository;
    private final AttendanceRepository attendanceRepository;


    @GetMapping("/info")
    public ResponseEntity<?> getUserInfoAndTodayGame(HttpServletRequest req) {

        // 현재 로그인한 사용자 memberId 가져오기
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        // 사용자 정보 조회
        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }

        User user = userOptional.get();
        String nickname = user.getNickname();
        String myClub = user.getMyClub();

        // 오늘 날짜 기준으로 경기 정보 조회
        LocalDate today = LocalDate.now();
        Optional<GameInfo> gameInfoOptional = gameInfoRepository.findByGameDateAndTeam(today, myClub);

        // 응답 JSON 구성
        Map<String, Object> response = new HashMap<>();
        response.put("nickname", nickname);

        if (gameInfoOptional.isPresent()) {
            response.put("gameInfo", GameInfoResponseDTO.fromEntity(gameInfoOptional.get()));
        } else {
            response.put("gameInfo", "오늘의 경기가 없습니다.");
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/viewAttendance")
    public ResponseEntity<?> getMonthlyAttendance(
            @RequestParam("month") int month,
            HttpServletRequest req) {

        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }
        User user = userOptional.get();

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        YearMonth selectedMonth = YearMonth.of(year, month);
        LocalDate startDate = selectedMonth.atDay(1);
        LocalDate endDate = selectedMonth.atEndOfMonth();

        List<LocalDate> attendedDates = attendanceRepository.findByUserAndDateBetween(user, startDate, endDate)
                .stream()
                .filter(Attendance::getIsAttendance)
                .map(Attendance::getDate)
                .collect(Collectors.toList());

        return ResponseEntity.ok(attendedDates);
    }

    // 출석 처리 API
    @PostMapping("/attendance")
    public ResponseEntity<?> markAttendance(HttpServletRequest req) {
        Long memberId = accountHelper.getMemberId(req);
        if (memberId == null) {
            return ResponseEntity.status(401).body("{\"status\": 401, \"message\": \"로그인이 필요합니다.\"}");
        }

        Optional<User> userOptional = userRepository.findById(memberId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("{\"status\": 404, \"message\": \"사용자를 찾을 수 없습니다.\"}");
        }
        User user = userOptional.get();

        LocalDate today = LocalDate.now();

        // 오늘 날짜 출석 여부 확인
        Optional<Attendance> existingAttendance = attendanceRepository.findByUserAndDate(user, today);

        if (existingAttendance.isPresent() && existingAttendance.get().getIsAttendance()) {
            return ResponseEntity.badRequest().body("{\"status\": 400, \"message\": \"이미 출석했습니다.\"}");
        }

        // 출석 정보 저장
        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setDate(today);
        attendance.setIsAttendance(true);

        attendanceRepository.save(attendance);

        return ResponseEntity.ok("{\"status\": 200, \"message\": \"출석되었습니다.\"}");
    }


}
