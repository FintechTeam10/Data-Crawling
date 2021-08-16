package team10.moneyTube.service.crawl;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;

@Data
@AllArgsConstructor
class Comment {
    private String word;
    private Integer times;
}
@Data
@AllArgsConstructor
class Reputation{
    private Integer cases;
    private Double ratio;
}
public class crawlSometrend {

    private static Integer stringToInt(String in){

        if(in.contains("건"))
            in = in.replace("건", "");
        if(in.contains(","))
            in = in.replace(",", "");
        return Integer.parseInt(in);
    }



    public static void main(String[] args) throws IOException {

        String URL = "https://some.co.kr/analysis/social/reputation";
        String filePath = "C:\\Tools\\chromedriver.exe";
        String find = "쯔양";

        try {
            System.setProperty("webdriver.chrome.driver", filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);


        driver.findElement(By.xpath("/html/body/div[13]/div[5]/div/p/a")).click();
        driver.findElement(By.xpath("//button[@type='button']")).click();
        try {Thread.sleep(1000);} catch (InterruptedException e) {}
        driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[2]/div[1]/form/fieldset/div/div[1]/div/input")).sendKeys(find);
        driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[2]/div[2]/div/div[2]/button")).click();
        try {Thread.sleep(6000);} catch (InterruptedException e) {}
        Document html = Jsoup.connect(URL).get();


        Elements tableList = html.select("#totalRankList > tr");
        ArrayList<Comment> positiveComments = new ArrayList<>();
        ArrayList<Comment> negativeComments = new ArrayList<>();
        ArrayList<Comment> neutralComments = new ArrayList<>();

        for(Element table : tableList) {
            if(!table.select("td.reputation.positive").isEmpty()) {
                Comment r = new Comment(table.select("td.keyword").text(),
                        Integer.parseInt(table.select("td.count").text()));
                positiveComments.add(r);
            }
            else if(!table.select("td.reputation.negative").isEmpty()) {
                Comment r = new Comment(table.select("td.keyword").text(),
                        Integer.parseInt(table.select("td.count").text()));
                negativeComments.add(r);
            }
            else if(!table.select("td.reputation.neutrality").isEmpty()) {
                Comment r = new Comment(table.select("td.keyword").text(),
                        Integer.parseInt(table.select("td.count").text()));
                neutralComments.add(r);
            }
        }

        Elements p = html.select("span.legend-value");
        Integer positiveNum = stringToInt(p.get(0).text());
        Integer negativeNum = stringToInt(p.get(1).text());
        Integer neutralNum = stringToInt(p.get(2).text());
        Integer sum = positiveNum + negativeNum + neutralNum;
        Reputation positive = new Reputation(positiveNum, positiveNum.doubleValue()/sum*100);
        Reputation negative = new Reputation(negativeNum, negativeNum.doubleValue()/sum*100);
        Reputation neutral = new Reputation(neutralNum, neutralNum.doubleValue()/sum*100);

    }
}
