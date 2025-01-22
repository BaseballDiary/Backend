package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CrawlingGameInfo {

    private final GameInfoRepository gameInfoRepository;
    private final List<GameInfo> list;

    @Transactional
    public List<GameInfo> crawling(String date){
        String url ="https://m.sports.naver.com/kbaseball/schedule/index?category=kbo&date=";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pwyic\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(url + date);    //브라우저에서 url로 이동한다.
            Thread.sleep(3000);

            getDataList(driver, date);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        gameInfoRepository.saveAll(list);
        return list;
    }
    public void getDataList(WebDriver driver, String date){
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div.ScheduleLeagueType_match_list_group__18ML9"));

        // 테이블 데이터 파싱
        for(WebElement tableElement : tableElements) {
            String pageSource = tableElement.getAttribute("outerHTML");
            Document doc = Jsoup.parse(pageSource);
            GameInfo gameInfo = new GameInfo();

            //날짜  ex) 8월 1일 => 2024-08-01
            Element column = doc.selectFirst("em.ScheduleLeagueType_title__2Kalm");
            String dateText = column.text();
            String[] parts = dateText.split(" \\(");  // 공백과 여는 괄호를 기준으로 분리
            dateText = parts[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
            // 문자열 date를 LocalDate로 변환
            LocalDate localDate = LocalDate.parse(dateText, formatter);
            String year=date.split("-")[0];
            // year를 사용해 연도를 설정
            localDate = localDate.withYear(Integer.parseInt(year));
            gameInfo.setGameDate(localDate);

            Elements matches = doc.select("li.MatchBox_match_item__3_D0Q");
            for (Element match : matches) {
                // 경기 시간
                String timeText = match.select("div.MatchBox_time__nIEfd").text();
                String[] lines = timeText.split("시간"); // 줄바꿈 기준으로 분리
                timeText = lines[1];
                gameInfo.setTime(timeText);

                //장소
                String place = match.select("div.MatchBox_stadium__13gft").text();
                String[] places = place.split("경기장"); // 줄바꿈 기준으로 분리
                place = places[1];
                gameInfo.setPlace(place);

                // 경기 상태
                String status = match.select("em.MatchBox_status__2pbzi").text();
                gameInfo.setGameStatus(status);

                if (status.equals("취소")) { //경기 취소인 경우
                    cancel(match, gameInfo);
                } else { //경기 취소가 아닌 경우
                    notCancel(match, gameInfo);
                }
            }
            list.add(gameInfo);
        }
    }
    public static void cancel(Element match, GameInfo gameInfo) {
        Elements teams = match.select("div.MatchBoxTeamArea_team_name__2G9t1");
        String team1 = teams.get(0)
                .selectFirst("strong.MatchBoxTeamArea_team__3aB4O")
                .text();
        gameInfo.setTeam1(team1);

        // 두 번째 팀 이름 추출
        String team2 = teams.get(1)
                .selectFirst("strong.MatchBoxTeamArea_team__3aB4O")
                .text();
        gameInfo.setTeam2(team2);
    }
    public static void notCancel(Element match, GameInfo gameInfo){
        // 승리 팀 정보
        Elements winner = match.select("div.MatchBoxTeamArea_type_winner__2o1Hm");
        for(Element winnerElement : winner) {
            String winnerTeam = winnerElement.select("strong.MatchBoxTeamArea_team__3aB4O").text();
            gameInfo.setTeam1(winnerTeam);

            String winnerScore = winnerElement.select("strong.MatchBoxTeamArea_score__1_YFB").text();
            gameInfo.setTeam1Score(winnerScore);
        }

        // 패배 팀 정보
        Elements loser = match.select("div.MatchBoxTeamArea_type_loser__2ym2q");
        for (Element loserElement : loser) {
            String loserTeam = loserElement.select("strong.MatchBoxTeamArea_team__3aB4O").text();
            gameInfo.setTeam2(loserTeam);

            String loserScore = loserElement.select("strong.MatchBoxTeamArea_score__1_YFB").text();
            gameInfo.setTeam2Score(loserScore);
        }
    }
}
