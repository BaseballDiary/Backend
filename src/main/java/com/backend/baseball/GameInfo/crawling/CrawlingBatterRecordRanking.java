package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.BatterRecordRanking;
import com.backend.baseball.GameInfo.repository.BatterRecordRankingRepository;
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

import java.util.List;

@Component
@RequiredArgsConstructor
public class CrawlingBatterRecordRanking {

    // 타자 기록 한번에 크롤링
    private static final String[] batterRecords = {
            "div[data-key='batAvg']", //타율
            "div[data-key='batRbi']", //타점
            "div[data-key='batHr']", //홈런
            "div[data-key='batSb']", //도루
            "div[data-key='batOps']", //OPS
    };
    private final List<BatterRecordRanking> list;
    private final BatterRecordRankingRepository batterRecordRankingRepository;

    @Transactional
    public List<BatterRecordRanking> crawling(String year) {
        String url ="https://sports.daum.net/record/kbo/batter?season=";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pwyic\\Downloads\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        try {
            driver.get(url + year);
            Thread.sleep(1000);  //5000 -> 1000

            for (String record : batterRecords) {
                getDataList(driver, record, year);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            driver.close();	//탭 닫기
            driver.quit();	//브라우저 닫기
        }
        batterRecordRankingRepository.saveAll(list);
        return list;
    }
    public void getDataList(WebDriver driver, String cssSelector, String year) {


        WebElement tableElement = driver.findElement(By.cssSelector(cssSelector));

        String pageSource = tableElement.getAttribute("outerHTML");
        Document doc = Jsoup.parse(pageSource);
        Element recordElement = doc.select("div strong").first();
        //기록
        String record = recordElement.text();

        Elements rows = doc.select("ol li");

        // 테이블 데이터 파싱
        for (Element row : rows) {
            BatterRecordRanking batterRecordRanking = new BatterRecordRanking();

            //년도
            batterRecordRanking.setYear(year);

            batterRecordRanking.setRecordType(record);

            //순위
            String ranking = row.select("em.num_ranking").text();
            batterRecordRanking.setRanking(ranking);

            //선수 이름 & 구단 이름
            String playerInfo = row.select("strong.tit_thumb").text();
            String[] parts = playerInfo.split(" \\(");  // (로 구분
            String playerName = parts[0]; // 선수 이름
            batterRecordRanking.setPlayerName(playerName);

            String club = parts[1].replace(")", "");  // 구단 이름, ")" 제거
            batterRecordRanking.setClub(club);

            //점수
            String score = row.select("span.num_value").text(); // 점수 값만 추출
            batterRecordRanking.setScore(score);

            list.add(batterRecordRanking);
        }
    }
}
