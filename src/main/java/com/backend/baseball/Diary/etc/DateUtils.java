package com.backend.baseball.Diary.etc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class DateUtils {
    private static final Map<DayOfWeek, String> KOREAN_DAYS = Map.of(
            DayOfWeek.MONDAY, "월",
            DayOfWeek.TUESDAY, "화",
            DayOfWeek.WEDNESDAY, "수",
            DayOfWeek.THURSDAY, "목",
            DayOfWeek.FRIDAY, "금",
            DayOfWeek.SATURDAY, "토",
            DayOfWeek.SUNDAY, "일"
    );

    public static String getKoreanDay(LocalDate date) {
        return KOREAN_DAYS.get(date.getDayOfWeek());
    }
}
