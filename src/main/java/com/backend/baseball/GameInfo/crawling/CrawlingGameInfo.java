package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.GameInfo;
import com.backend.baseball.GameInfo.repository.GameInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class CrawlingGameInfo {

    private final GameInfoRepository gameInfoRepository;

    @Value("${webdriver.chrome.path}")
    private String chromeDriverPath;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<GameInfo> crawling(String date){
        String url ="https://m.sports.naver.com/kbaseball/schedule/index?category=kbo&date=";

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        // ChromeOptions 추가 (AWS 서버에서도 작동하도록 설정)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // GUI 없이 실행 (서버 환경 필수)
        options.addArguments("--no-sandbox"); // 보안 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // 메모리 문제 해결
        options.addArguments("--disable-gpu"); // GPU 비활성화

        WebDriver driver = new ChromeDriver(options); // 옵션 추가된 WebDriver 생성

        List<GameInfo> list= new ArrayList<>();
        try {
            driver.get(url + date);    //브라우저에서 url로 이동한다.
            Thread.sleep(5000);

            // 리다이렉션 확인: 현재 페이지의 URL이 원래 요청한 날짜와 다른 경우
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.endsWith(date)) {
                log.info("오늘의 경기 정보 없음");
                return null;  //DB에 해당 데이터도 없는데 리다이렉션 되는 경우 => 경기가 없는 날짜 !

            } else {
                getDataList(driver, date, list);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        gameInfoRepository.saveAll(list);
        return list;
    }
    public void getDataList(WebDriver driver, String date, List<GameInfo> list){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        List<WebElement> tableElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("div.ScheduleLeagueType_match_list_group__18ML9")));

        /*// "경기장"이 포함된 요소 찾기
        List<WebElement> allElements = driver.findElements(By.xpath("//*[contains(text(), '경기장')]"));

        if (!allElements.isEmpty()) {
            for (WebElement element : allElements) {
                System.out.println("경기장 포함 요소: " + element.getAttribute("outerHTML"));
            }
        } else {
            System.out.println("경기장이 포함된 요소를 찾을 수 없습니다.");
        }*/
        //List<WebElement> tableElements = driver.findElements(By.cssSelector("div.ScheduleLeagueType_match_list_group__18ML9"));

        // 테이블 데이터 파싱
        for(WebElement tableElement : tableElements) {

            String pageSource = tableElement.getAttribute("outerHTML");
            Document doc = Jsoup.parse(pageSource);

            //날짜  ex) 8월 1일 => 2024-08-01
            Element column = doc.selectFirst("em.ScheduleLeagueType_title__2Kalm");
            String dateText = column.text();
            String[] parts = dateText.split(" \\(");  // 공백과 여는 괄호를 기준으로 분리
            dateText = parts[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN);
            TemporalAccessor parsed = formatter.parse(dateText);
            int month = parsed.get(ChronoField.MONTH_OF_YEAR);
            int day = parsed.get(ChronoField.DAY_OF_MONTH);
            String year=date.split("-")[0];
            LocalDate localDate = LocalDate.of(Integer.parseInt(year), month, day);

            Elements matches = doc.select("li.MatchBox_match_item__3_D0Q");
            for (Element match : matches) {
                GameInfo gameInfo = new GameInfo();
                gameInfo.setGameDate(localDate);

                // 경기 시간
                String timeText = match.select("div.MatchBox_time__nIEfd").text();
                String[] lines = timeText.split("시간"); // 줄바꿈 기준으로 분리
                timeText = lines[1];
                gameInfo.setTime(timeText);

                //장소
                //String place = match.select("div.MatchBox_stadium__13gft").text().replace("경기장", "").trim();
                //String place = match.selectFirst("div.MatchBox_stadium__13gft").text();
                //log.info("경기 장소 출력 잘 되는지 확인 : "+ place);
                //gameInfo.setPlace(place);
                //String[] places = place.split("경기장"); // 줄바꿈 기준으로 분리
                //place = places[1];
                try {
                    Element placeElement = match.selectFirst("div.MatchBox_stadium__13gft");
                    if (placeElement != null) {
                        String place = placeElement.text().trim();
                        log.info("경기 장소: " + place);
                        gameInfo.setPlace(place);
                    } else {
                        log.warn("경기 장소를 찾을 수 없음! 선택자 확인 필요");
                    }
                } catch (Exception e) {
                    log.error("경기 장소 추출 중 오류 발생", e);
                }

                // 경기 상태
                String status = match.select("em.MatchBox_status__2pbzi").text();
                gameInfo.setGameStatus(status);

                if (status.equals("취소")) { //경기 취소인 경우
                    cancel(match, gameInfo);
                } else { //경기 취소가 아닌 경우
                    notCancel(match, gameInfo);
                }
                if (gameInfo.getTeam1() == null || gameInfo.getTeam2() == null) {
                    log.warn("누락된 데이터 발견! 저장하지 않음: " + gameInfo.getGameDate());
                    continue; // null 값이 포함된 레코드는 저장하지 않음
                }

                list.add(gameInfo);
            }
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

        if(winner.isEmpty()) { //동점인 경우
            Elements tiedElements = match.select("div.MatchBoxTeamArea_team_item__3w5mq"); //동점
            if (tiedElements.size() < 2) {
                log.warn("동점 경기 div 개수가 2개 미만! HTML 구조 변경 가능성 확인 필요");
            } else {
                Element team1Element = tiedElements.get(0);
                Element team2Element = tiedElements.get(1);

                String team1 = team1Element.selectFirst("strong.MatchBoxTeamArea_team__3aB4O").text();
                gameInfo.setTeam1(team1);

                String team1Score = team1Element.select("strong.MatchBoxTeamArea_score__1_YFB").text();
                gameInfo.setTeam1Score(team1Score);

                String team2 = team2Element.select("strong.MatchBoxTeamArea_team__3aB4O").text();
                gameInfo.setTeam2(team2);

                gameInfo.setTeam2Score(team1Score); //동점이니까

                return;
            }
        }

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
