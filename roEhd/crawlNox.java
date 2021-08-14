package team10.moneyTube.service.crawl;
import java.io.IOException;
import java.util.ArrayList;

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

    public static Double stringToDouble(String in){

        Double result;
        if(in.contains(("￦")))
            in = in.replace("￦", "").trim();
        if(in.equals("-"))
            return null;
        if(in.contains("만")){
            result = Double.parseDouble(in.replace("만", ""));
            return result * 10000;
        }
        if (in.contains("억")) {
            result = Double.parseDouble(in.replace("억", ""));
            return result * 100000000;
        }
        return Double.parseDouble(in);
    }

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
        try {Thread.sleep(3000);} catch (InterruptedException e) {}

        //구독자 랭킹 '지역' 으로 변경
        driver.findElement(By.xpath("//*[@id=\"tab-channel\"]/div[1]/div/div[1]/div[1]/div/ul/li[2]/a")).click();
        Document html = Jsoup.connect(URL).get();

        //지역 랭킹
        String rank = html.select("#area-rank > a").text();

        //구독자, 조회수, 평균조회수, 동영상, 라이브수입
        Elements baseData = html.select("ul.base-data").select("span.strong");

        Double subscriber = stringToDouble(baseData.get(0).text());
        Double view = stringToDouble(baseData.get(1).text());
        Double avgView = stringToDouble(baseData.get(2).text());
        Double video = stringToDouble(baseData.get(3).text());
        Double liveIncome = stringToDouble(baseData.get(4).text());

        //태그
        Elements tagData = html.select("ul.tag-list").select("li.tag-item");

        ArrayList<String> tags = new ArrayList<String>();
        for(int i=0;i<tagData.size();i++)
            tags.add(tagData.get(i).text());

/*

       //유튜브 채널 통계표 '30일' 로 변경
        driver.findElement(By.xpath("//*[@id=\"tab-channel\"]/div[3]/div[1]/div/span[3]")).click();
        Document doc = Jsoup.connect(URL).get();
        Elements tableData = doc.select("table.tabla-ui-content");
        System.out.println(tableData);

        //nox 보고서

*/




    }
}
