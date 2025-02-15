package com.backend.baseball.Home.service;

import com.backend.baseball.Home.dto.AttendanceResponseDTO;
import com.backend.baseball.Home.entity.Attendance;
import com.backend.baseball.Home.repository.AttendanceRepository;
import com.backend.baseball.User.repository.UserRepository;
import com.backend.baseball.User.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    //특정 월 & 전달의 출석 정보 조회
    public AttendanceResponseDTO getAttendanceData(int year, int month, Long certificatedId) {
        User user = userRepository.findById(certificatedId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        //요청한 월과 전달의 날짜 리스트 가져오기
        YearMonth currentMonth = YearMonth.of(year, month);
        YearMonth previousMonth = currentMonth.minusMonths(1);

        //해당 월과 전달의 모든 날짜 생성
        List<LocalDate> allDates = getAllDates(previousMonth, currentMonth);

        //출석 데이터 조회
        List<Attendance> attendanceList = attendanceRepository.findByUserAndDateIn(user, allDates);

        //출석 데이터를 ResponseDTO로 변환
        List<AttendanceResponseDTO.AttendanceData> attendanceDataList = allDates.stream()
                .map(date -> {
                    boolean isAttended = attendanceList.stream()
                            .anyMatch(a -> a.getDate().equals(date) && a.getIsAttendance());
                    return new AttendanceResponseDTO.AttendanceData(date.toString(), isAttended ? 1 : 0);
                })
                .collect(Collectors.toList());

        return new AttendanceResponseDTO(attendanceDataList);
    }

   //특정 월과 전달의 모든 날짜 리스트 반환
    private List<LocalDate> getAllDates(YearMonth previousMonth, YearMonth currentMonth) {
        return previousMonth.atEndOfMonth().datesUntil(currentMonth.atEndOfMonth().plusDays(1))
                .collect(Collectors.toList());
    }

    //사용자 출석률 계산. 반올림하여 정수로 반환
     public int calculateTemperature(Long certificatedId, int year, int month) {
        User user = userRepository.findById(certificatedId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        // 현재 월의 총 일수 구하기
        YearMonth currentMonth = YearMonth.of(year, month);
        int totalDays = currentMonth.lengthOfMonth();

        // 현재 월의 모든 날짜 리스트 생성
        List<LocalDate> allDates = currentMonth.atDay(1)
                .datesUntil(currentMonth.atEndOfMonth().plusDays(1))
                .toList();

        // 출석한 날짜 리스트 조회
        List<Attendance> attendanceList = attendanceRepository.findByUserAndDateIn(user, allDates);
        long attendedDays = attendanceList.stream().filter(Attendance::getIsAttendance).count();

        // 출석률 계산: (출석일수 / 총 일수) * 99 → 반올림하여 정수로 반환
        return (int) Math.round((double) attendedDays / totalDays * 99);
    }

}
