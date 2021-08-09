package team10.moneyTube.service.crawl;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.select.Elements;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class crawlNox {
    public static void main(String[] args) throws IOException {

        String URL = "https://kr.noxinfluencer.com/youtube/channel/UCtCiO5t2voB14CmZKTkIzPQ";
        String filePath = "C:\\Tools\\chromedriver.exe";

        try {
            System.setProperty("webdriver.chrome.driver", filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChromeOptions options = new ChromeOptions();
        //옵션 - 브라우저가 눈에 보이지 않도록 설정
        //options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);
        //HTTP응답속도보다 자바의 컴파일 속도가 더 빠르기에 대기
        try {Thread.sleep(1000);} catch (InterruptedException e) {}

/*
        //구독자 랭킹 '지역' 으로 변경
        driver.findElement(By.xpath("//*[@id=\"tab-channel\"]/div[1]/div/div[1]/div[1]/div/ul/li[2]/a")).click();
        Document html = Jsoup.connect(URL).get();

        //지역 랭킹
        String rank = html.select("#area-rank > a").text();
        //구독자, 조회수, 평균조회수, 동영상, 라이브수입
        Elements baseData = html.select("ul.base-data").select("span.strong");
        String subscriber = baseData.get(0).text();
        String view = baseData.get(1).text();
        String avgView = baseData.get(2).text();
        Integer video = Integer.parseInt(baseData.get(3).text());
        String liveIncome = baseData.get(4).text();
*/



        //유튜브 채널 통계표 '30일' 로 변경
        driver.findElement(By.xpath("//*[@id=\"tab-channel\"]/div[3]/div[1]/div/span[3]")).click();
        Document doc = Jsoup.connect(URL).get();
        Elements tableData = doc.select("div.table-content").select("table.tabla-ui-content");
        System.out.println(tableData);



        //nox 보고서

    }
}
