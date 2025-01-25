package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.TeamRanking;
import com.backend.baseball.GameInfo.repository.TeamRankingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlingTeamRanking {

    private final TeamRankingRepository teamRankingRepository;

    public List<TeamRanking> crawling(String year) {
        String URL = "https://sports.news.naver.com/kbaseball/record/index?category=kbo&year=";
        Document doc;
        List<TeamRanking> list = new ArrayList<>();

        try {
            doc = Jsoup.connect(URL + year).get();

            // <tbody id="regularTeamRecordList_table"> 요소 찾기
            Element table = doc.select("tbody#regularTeamRecordList_table").first();

            // 각 <tr> 요소(팀 정보) 순회
            Elements rows = table.select("tr");
            for (Element row : rows) {
                TeamRanking teamRanking = new TeamRanking();
                // 팀 순위
                String ranking = row.select("th strong").text();
                teamRanking.setRanking(ranking);

                // 팀 이름
                String teamName = row.select("td span").get(1).text();
                teamRanking.setTeamName(teamName);

                // 경기 수
                String gamesNum = row.select("td span").get(2).text();
                teamRanking.setTeamName(gamesNum);

                // 승수
                String win = row.select("td span").get(3).text();
                teamRanking.setWin(win);

                // 패수
                String lose = row.select("td span").get(4).text();
                teamRanking.setLose(lose);

                // 무수
                String tie = row.select("td span").get(5).text();
                teamRanking.setTie(tie);

                // 승률
                String winningRate = row.select("td strong").text();
                teamRanking.setWinningRate(winningRate);

                // 게임차
                String gameDiff = row.select("td span").get(6).text();
                teamRanking.setGameDiff(gameDiff);

                // 연속
                String streak = row.select("td span").get(7).text();
                teamRanking.setStreak(streak);

                // 출루율
                String obp = row.select("td span").get(8).text();
                teamRanking.setOdp(obp);

                // 장타율
                String slg = row.select("td span").get(9).text();
                teamRanking.setSlg(slg);

                //최근 10경기
                String last10Games = row.select("td span").get(10).text();
                teamRanking.setLast10Games(last10Games);
                list.add(teamRanking);
            }
        } catch (
                IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return teamRankingRepository.saveAll(list); //TeamRanking 리스트 전체 저장
    }
}
