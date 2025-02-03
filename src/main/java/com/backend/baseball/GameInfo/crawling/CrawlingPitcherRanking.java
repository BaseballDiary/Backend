package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.PitcherRanking;
import com.backend.baseball.GameInfo.entity.PitcherRecordRanking;
import com.backend.baseball.GameInfo.repository.PitcherRankingRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlingPitcherRanking {

    private final PitcherRankingRepository pitcherRankingRepository;
    private final List<PitcherRanking> list;

    //@Value("${webdriver.chrome.path}")
    //private String chromeDriverPath;

    @Transactional
    public List<PitcherRanking> crawling(String year) {
        String url ="https://sports.daum.net/record/kbo/pitcher?season=";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pwyic\\Downloads\\chromedriver-win64\\chromedriver.exe");
        // ChromeOptions 추가 (AWS 서버에서도 작동하도록 설정)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // GUI 없이 실행 (서버 환경 필수)
        options.addArguments("--no-sandbox"); // 보안 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // 메모리 문제 해결
        options.addArguments("--disable-gpu"); // GPU 비활성화

        WebDriver driver = new ChromeDriver(options); // 옵션 추가된 WebDriver 생성

        try {
            driver.get(url + year);    //브라우저에서 url로 이동한다.
            Thread.sleep(1000);  //5000 -> 1000

             getDataList(driver, year);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        pitcherRankingRepository.saveAll(list);
        return list;
    }
    public void getDataList(WebDriver driver, String year) {

        WebElement tableElement = driver.findElement(By.cssSelector("table.tbl_record"));
        String pageSource = tableElement.getAttribute("outerHTML");
        Document doc = Jsoup.parse(pageSource);
        Elements rows = doc.select("tr");

        // 테이블 데이터 파싱
        for(int i=1; i<=20; i++){ //기록 별 5명씩
            PitcherRanking pitcherRanking = new PitcherRanking();
            Element row = rows.get(i);
            Elements columns = row.select("td");

            //년도
            pitcherRanking.setYear(year);
            //순위
            String ranking = columns.get(0).text();
            pitcherRanking.setRanking(ranking);

            //선수 이름
            String playerName = columns.get(1).text();
            pitcherRanking.setPlayerName(playerName);

            //구단 이름
            String club = columns.get(2).text();
            pitcherRanking.setClub(club);

            //평균 자책
            String era = columns.get(3).text();
            pitcherRanking.setEra(era);

            //경기수
            String gameNum = columns.get(4).text();
            pitcherRanking.setGameNum(gameNum);

            //승
            String win  = columns.get(5).text();
            pitcherRanking.setWin(win);

            //패
            String lose = columns.get(6).text();
            pitcherRanking.setLose(lose);

            //이닝
            String inning = columns.get(7).text();
            pitcherRanking.setInning(inning);

            //세이브
            String save = columns.get(8).text();
            pitcherRanking.setSave(save);

            //홀드
            String hold = columns.get(9).text();
            pitcherRanking.setHold(hold);

            //투구수
            String pitchCount = columns.get(10).text();
            pitcherRanking.setPitchCount(pitchCount);

            //피안타
            String hitsAllowed = columns.get(11).text();
            pitcherRanking.setHitsAllowed(hitsAllowed);

            //피홈런
            String homeRunsAllowed = columns.get(12).text();
            pitcherRanking.setHomeRunsAllowed(homeRunsAllowed);

            //탈삼진
            String strikeout = columns.get(13).text();
            pitcherRanking.setStrikeout(strikeout);

            //사시구
            String walk = columns.get(14).text();
            pitcherRanking.setWalk(walk);

            //실점
            String runsAllowed = columns.get(15).text();
            pitcherRanking.setRunsAllowed(runsAllowed);

            //자책
            String earnedRun = columns.get(16).text();
            pitcherRanking.setEarnedRun(earnedRun);

            //WHIP
            String whip = columns.get(17).text();
            pitcherRanking.setWhip(whip);

            //QS
            String qs = columns.get(18).text();
            pitcherRanking.setQs(qs);

            list.add(pitcherRanking);
        }
    }
}
