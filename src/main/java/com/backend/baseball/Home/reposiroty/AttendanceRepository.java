package com.backend.baseball.Home.reposiroty;

import com.backend.baseball.Home.entity.Attendance;
import com.backend.baseball.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);

    Optional<Attendance> findByUserAndDate(User user, LocalDate date);

}
