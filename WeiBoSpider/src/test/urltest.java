package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import dowmload.download;

public class urltest {

	private static final String Cookie = "ALF=1573366720; SCF=AgCC-Sxlratg_LMnVwYk9b_cQyCnFa1U2Nbho8mmISEwEzv-4zmPhYBZvwbAPdPYMjZDZBi1g_HmBIx_DkPawRg.; SUB=_2A25wpG6RDeRhGeRL7FYT9yfLzzmIHXVQZ3LZrDV6PUNbktANLWzTkW1NUvuhZEaT6EGOUvm9TQ5hF1RyTmSAakHa; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhCshDP3WXAljUR1iXHpp.C5JpX5KMhUgL.FozfS0BES0.NSh-2dJLoIceLxKBLB.BLBK5LxKBLBonL12BLxK-L1h-L1h-LxKBLB.eL1-2LxK-L1K2L1KnLxK-LBo5LBo2LxK-L1hnLB-zLxKqL1heLBoeLxKqL1KBLBo.LxK-L1K5L12BLxK-LB-BL1KMt; SUHB=0XD0bME6dmfSw0; _T_WM=0486b3de43e85b72d4dabf4b8539a23c";

	public static void mmain(String[] args) throws InterruptedException {
		//System.setProperty("webdriver.chrome.marionette","D:\\driver\\chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://www.gzccc.edu.cn");
		System.out.printf("now accesss %s \n", driver.getCurrentUrl());
		Thread.sleep(2000);
		driver.findElement(By.linkText("数字校园")).click();
	    System.out.printf("now accesss %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	    driver.navigate().back();
	    System.out.printf("now accesss %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	    driver.navigate().forward();
	    System.out.printf("forward to %s \n", driver.getCurrentUrl());
	    Thread.sleep(2000);
	    driver.navigate().refresh();
		//driver.close();
	}
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("--save-page-as-mhtml");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://m.weibo.cn/s/video/show?object_id=1034%3A4424790128153513&fromWap=1");
		Thread.sleep(5000);
		String htmlString=driver.getPageSource();
		Document document=Jsoup.parse(htmlString);
		Elements videos=document.select("video[id=vjs_video_3_html5_api]");
		String videourl="";
		for (Element video : videos) {
			videourl=video.attr("src");
			videourl=videourl.replace("&amp;", "&");
		}
		System.out.println(videourl);
		download.getvideo(videourl, "D:/weibo/video");
	}
	public static void _main(String[] args) {
		//https://m.weibo.cn/s/video/show?object_id=1034:4424108839245820&amp&fromWap=1
		// https://m.weibo.cn/s/video/show?object_id=1034%3A4424790128153513&fromWap=1
		String urlString = "https://m.weibo.cn/s/video/show?object_id=1034%3A4424790128153513&fromWap=1";
		String Cookie = "ALF=1573366720; SCF=AgCC-Sxlratg_LMnVwYk9b_cQyCnFa1U2Nbho8mmISEwEzv-4zmPhYBZvwbAPdPYMjZDZBi1g_HmBIx_DkPawRg.; SUB=_2A25wpG6RDeRhGeRL7FYT9yfLzzmIHXVQZ3LZrDV6PUNbktANLWzTkW1NUvuhZEaT6EGOUvm9TQ5hF1RyTmSAakHa; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhCshDP3WXAljUR1iXHpp.C5JpX5KMhUgL.FozfS0BES0.NSh-2dJLoIceLxKBLB.BLBK5LxKBLBonL12BLxK-L1h-L1h-LxKBLB.eL1-2LxK-L1K2L1KnLxK-LBo5LBo2LxK-L1hnLB-zLxKqL1heLBoeLxKqL1KBLBo.LxK-L1K5L12BLxK-LB-BL1KMt; SUHB=0XD0bME6dmfSw0; _T_WM=0486b3de43e85b72d4dabf4b8539a23c";
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("authority", "weibo.cn");
			connection.setRequestProperty("accept",
					": text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
			connection.setRequestProperty("accept-language", "zh,zh-CN;q=0.9,en;q=0.8");
			connection.setRequestProperty("user-agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
			connection.setRequestProperty("Cookie", Cookie);
			connection.connect();
			Map<String, List<String>> map = connection.getHeaderFields();
			System.out.println(map.toString());
			// download.getpic(map.get("Location").get(0), "D:/weibo/a");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
