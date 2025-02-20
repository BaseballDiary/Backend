package com.backend.baseball.GameInfo.crawling;


import com.backend.baseball.GameInfo.entity.BatterRanking;
import com.backend.baseball.GameInfo.entity.PitcherRecordRanking;
import com.backend.baseball.GameInfo.repository.BatterRankingRepository;
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
public class CrawlingBatterRanking {
    private final BatterRankingRepository batterRankingRepository;

    @Value("${webdriver.chrome.path}")
    private String chromeDriverPath;

    @Transactional
    public List<BatterRanking> crawling(String year) {
        String url ="https://sports.daum.net/record/kbo/batter?season=";

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        // ChromeOptions 추가 (AWS 서버에서도 작동하도록 설정)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // GUI 없이 실행 (서버 환경 필수)
        options.addArguments("--no-sandbox"); // 보안 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // 메모리 문제 해결
        options.addArguments("--disable-gpu"); // GPU 비활성화

        WebDriver driver = new ChromeDriver(options); // 옵션 추가된 WebDriver 생성

        List<BatterRanking> list = new ArrayList<>();
        try {
            driver.get(url + year);    //브라우저에서 url로 이동한다.
            Thread.sleep(1000);  //5000 -> 1000

            getDataList(driver, year, list);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기

        batterRankingRepository.saveAll(list);
        return batterRankingRepository.findByYear(year);
    }
    public void getDataList(WebDriver driver, String year, List<BatterRanking> list){

        WebElement tableElement = driver.findElement(By.cssSelector("table.tbl_record"));
        String pageSource = tableElement.getAttribute("outerHTML");
        Document doc = Jsoup.parse(pageSource);
        Elements rows = doc.select("tr");

        // 테이블 데이터 파싱
        for(int i=1; i<=20; i++){
            BatterRanking batterRanking = new BatterRanking();
            Element row = rows.get(i);
            Elements columns = row.select("td");

            //년도
            batterRanking.setYear(year);

            //순위
            String ranking = columns.get(0).text();
            batterRanking.setRanking(ranking);

            //선수 이름
            String playerName = columns.get(1).text();
            batterRanking.setPlayerName(playerName);

            //구단 이름
            String club = columns.get(2).text();
            batterRanking.setClub(club);

            //경기 수
            String gameNum = columns.get(3).text();
            batterRanking.setGameNum(gameNum);

            //타석
            String atBat = columns.get(4).text();
            batterRanking.setAtBat(atBat);

            //타수
            String plateAppear = columns.get(5).text();
            batterRanking.setPlateAppear(plateAppear);

            //안타
            String single = columns.get(6).text();
            batterRanking.setSingle(single);

            //2타
            String doubleHit = columns.get(7).text();
            batterRanking.setDoubleHit(doubleHit);

            //3타
            String tripleHit = columns.get(8).text();
            batterRanking.setTripleHit(tripleHit);

            //홈런
            String homeRun = columns.get(9).text();
            batterRanking.setHomeRun(homeRun);

            //타점
            String rbis = columns.get(10).text();
            batterRanking.setRbis(rbis);

            //득점
            String run = columns.get(11).text();
            batterRanking.setRun(run);

            //도루
            String stolenBase = columns.get(12).text();
            batterRanking.setStolenBase(stolenBase);

            //사시구
            String walk = columns.get(13).text();
            batterRanking.setWalk(walk);

            //삼진
            String strikeout = columns.get(14).text();
            batterRanking.setStrikeout(strikeout);

            //타율
            String ba = columns.get(15).text();
            batterRanking.setBa(ba);

            //출루율
            String obp = columns.get(16).text();
            batterRanking.setObp(obp);

            //장타율
            String slg = columns.get(17).text();
            batterRanking.setSlg(slg);

            //OPS
            String ops = columns.get(18).text();
            batterRanking.setOps(ops);

            list.add(batterRanking);
        }
    }
}
