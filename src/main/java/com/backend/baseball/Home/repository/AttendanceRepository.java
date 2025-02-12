package com.backend.baseball.Home.repository;

import com.backend.baseball.Home.entity.Attendance;
import com.backend.baseball.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    //특정 사용자의 특정 날짜 목록에 대한 출석 정보 조회
    List<Attendance> findByUserAndDateIn(User user, List<LocalDate> dates);

}
