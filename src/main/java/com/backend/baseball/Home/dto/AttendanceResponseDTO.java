package com.backend.baseball.Home.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class AttendanceResponseDTO {
    private List<AttendanceData> attendance;

    @Getter
    @AllArgsConstructor
    public static class AttendanceData {
        private String date;
        private int attend; // 0 = 미출석, 1 = 출석
    }
}