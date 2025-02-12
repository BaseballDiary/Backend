package com.backend.baseball.Diary.dto.viewDiary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatsResponseDTO {
    private MyStatsDTO myStats;
    private TeamStatsDTO teamStats;
}
