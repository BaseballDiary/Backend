package com.backend.baseball.GameInfo.crawling;

import com.backend.baseball.GameInfo.entity.PitcherRanking;
import com.backend.baseball.GameInfo.entity.PitcherRecordRanking;
import com.backend.baseball.GameInfo.repository.PitcherRecordRankingRepository;
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
public class CrawlingPitcherRecordRanking {

    // 투수 기록 한번에 크롤링
    private static final String[] pitcherRecords = {
            "div[data-key='pitW']", //다승
            "div[data-key='pitEra']", //평균 자책
            "div[data-key='pitSo']", //탈삼진
            "div[data-key='pitSv']", //세이브
            "div[data-key='pitWhip']" //WHIP
    };
    private final PitcherRecordRankingRepository recordRankingRepository;

    @Value("${webdriver.chrome.path}")
    private String chromeDriverPath;

    @Transactional
    public List<PitcherRecordRanking> crawling(String year) {
        String url ="https://sports.daum.net/record/kbo/pitcher?season=";

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        // ChromeOptions 추가 (AWS 서버에서도 작동하도록 설정)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");  // GUI 없이 실행 (서버 환경 필수)
        options.addArguments("--no-sandbox"); // 보안 샌드박스 비활성화
        options.addArguments("--disable-dev-shm-usage"); // 메모리 문제 해결
        options.addArguments("--disable-gpu"); // GPU 비활성화

        WebDriver driver = new ChromeDriver(options); // 옵션 추가된 WebDriver 생성

        List<PitcherRecordRanking> list = new ArrayList<>();

        try {
            driver.get(url + year);
            Thread.sleep(1000);  //5000 -> 1000

            for (String record : pitcherRecords) {
                getDataList(driver, record, year, list);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            driver.close();	//탭 닫기
            driver.quit();	//브라우저 닫기
        }
        recordRankingRepository.saveAll(list);
        return recordRankingRepository.findByYear(year);
    }
    public void getDataList(WebDriver driver, String cssSelector, String year, List<PitcherRecordRanking> list) {
        WebElement tableElement = driver.findElement(By.cssSelector(cssSelector));

        String pageSource = tableElement.getAttribute("outerHTML");
        Document doc = Jsoup.parse(pageSource);
        Element recordElement = doc.select("div strong").first();
        //기록
        String record = recordElement.text();

        Elements rows = doc.select("ol li");

        // 테이블 데이터 파싱
        for(Element row : rows) {
            PitcherRecordRanking pitcherRecordRanking = new PitcherRecordRanking();

            //년도
            pitcherRecordRanking.setYear(year);

            pitcherRecordRanking.setRecordType(record);

            //순위
            String ranking = row.select("em.num_ranking").text();
            pitcherRecordRanking.setRanking(ranking);

            //선수 이름 & 구단 이름
            String playerInfo = row.select("strong.tit_thumb").text();
            String[] parts = playerInfo.split(" \\(");  // (로 구분
            String playerName = parts[0]; // 선수 이름
            pitcherRecordRanking.setPlayerName(playerName);

            String club = parts[1].replace(")", "");  // 구단 이름, ")" 제거
            pitcherRecordRanking.setClub(club);

            //점수
            String score = row.select("span.num_value").text(); // 점수 값만 추출
            pitcherRecordRanking.setScore(score);

            list.add(pitcherRecordRanking);
        }
    }
}
