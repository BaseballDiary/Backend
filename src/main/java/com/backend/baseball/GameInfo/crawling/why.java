package com.backend.baseball.GameInfo.crawling;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class why {
    public static void process(){
        String url ="https://m.sports.naver.com/kbaseball/schedule/index?category=kbo&date=";

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\pwyic\\Downloads\\chromedriver-win64\\chromedriver.exe");
        //크롬 드라이버 셋팅 (드라이버 설치한 경로 입력)
        //ChromeDriver 실행 파일의 경로를 셀레니움의 webdriver.chrome.driver 시스템 속성에 설정

        WebDriver driver = new ChromeDriver();//브라우저 선택

        String date = "2024-08-01";
        try {
            driver.get(url + date);    //브라우저에서 url로 이동한다.
            Thread.sleep(4000);

            getDataList(driver);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        driver.close();	//탭 닫기
        driver.quit();	//브라우저 닫기
    }
    public static void getDataList(WebDriver driver){
        List<String> list = new ArrayList<>();
        List<WebElement> tableElements = driver.findElements(By.cssSelector("div.ScheduleLeagueType_match_list_group__18ML9"));

        // 테이블 데이터 파싱
        for(WebElement tableElement : tableElements) {
            String pageSource = tableElement.getAttribute("outerHTML");
            Document doc = Jsoup.parse(pageSource);

            StringBuilder rowData = new StringBuilder();

            //날짜
            Element column = doc.selectFirst("em.ScheduleLeagueType_title__2Kalm");
            String dateText = column.text();
            String[] parts = dateText.split(" \\(");  // 공백과 여는 괄호를 기준으로 분리
            dateText = parts[0];
            rowData.append(dateText).append("\n");

            Elements matches = doc.select("li.MatchBox_match_item__3_D0Q");
            for (Element match : matches) {
                // 경기 시간
                String timeText = match.select("div.MatchBox_time__nIEfd").text();
                String[] lines = timeText.split("시간"); // 줄바꿈 기준으로 분리
                timeText = lines[1];
                rowData.append(timeText + "\t");

                //장소
                String place = match.select("div.MatchBox_stadium__13gft").text();
                String[] places = place.split("경기장"); // 줄바꿈 기준으로 분리
                place = places[1];
                rowData.append(place + "\t");

                // 경기 상태
                String status = match.select("em.MatchBox_status__2pbzi").text();
                rowData.append(status + "\t");

                if (status.equals("취소")) { //경기 취소인 경우
                    cancel(match, rowData);
                } else { //경기 취소가 아닌 경우
                    notCancel(match, rowData);
                }
            }

            System.out.println(rowData);
            list.add(rowData.toString());
            System.out.println("--------------------------------");
        }
    }
    public static void cancel(Element match, StringBuilder rowData) {
        Elements teams = match.select("div.MatchBoxTeamArea_team_name__2G9t1");
        String team1 = teams.get(0)
                .selectFirst("strong.MatchBoxTeamArea_team__3aB4O")
                .text();
        rowData.append(team1).append("\t");

        // 두 번째 팀 이름 추출
        String team2 = teams.get(1)
                .selectFirst("strong.MatchBoxTeamArea_team__3aB4O")
                .text();
        rowData.append(team2).append("\n");
    }
    public static void notCancel(Element match, StringBuilder rowData){
        // 승리 팀 정보
        Elements winner = match.select("div.MatchBoxTeamArea_type_winner__2o1Hm");
        for(Element winnerElement : winner) {
            String winnerTeam = winnerElement.select("strong.MatchBoxTeamArea_team__3aB4O").text();
            rowData.append(winnerTeam + "\t");
            String winnerScore = winnerElement.select("strong.MatchBoxTeamArea_score__1_YFB").text();
            rowData.append(winnerScore + "\t");
        }

        // 패배 팀 정보
        Elements loser = match.select("div.MatchBoxTeamArea_type_loser__2ym2q");
        for (Element loserElement : loser) {
            String loserTeam = loserElement.select("strong.MatchBoxTeamArea_team__3aB4O").text();
            rowData.append(loserTeam + "\t");
            String loserScore = loserElement.select("strong.MatchBoxTeamArea_score__1_YFB").text();
            rowData.append(loserScore + "\n");
        }
    }
    public static void main(String[] args) throws ParseException {

        process();
    }
}
