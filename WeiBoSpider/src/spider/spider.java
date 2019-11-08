package spider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.naming.spi.DirStateFactory.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import dowmload.download;
import dowmload.downloadUserPic;
import weibo.Weibo;

public class spider {
	String Cookie = null;// cookie
	String itemId = null;// 微博的id
	String uid = null;// 博主的uid

	public spider(String Cookie, String itemId, String uid) {
		this.Cookie = Cookie;
		this.itemId = itemId;
		this.uid = uid;
	}
	/**
	 * 获取微博评论里的图片
	 * */
	public void getPicUrl(int i) {
		Document doc;
		try {
			String url;
			// 判断翻页
			if (i > 1) {
				url = "https://weibo.cn/comment/" + itemId + "?uid=" + uid + "&rl=0&gid=10001&page=" + i;
			} else {
				url = "https://weibo.cn/comment/" + itemId + "?uid=" + uid + "&rl=0&gid=10001#cmtfrm";
			}
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			Elements jpgs = doc.select("a[href$=.jpg]");
			Elements pngs = doc.select("a[href$=.png]");
			for (Element element : jpgs) {
				String urlString = element.attr("href").toString();
				System.out.println(urlString);
				download.getpic(urlString.toString(), "D:/WeiBo_image" + "/" + uid + "/" +
				 itemId);
			}
			for (Element element : pngs) {
				String urlString = element.attr("href").toString();
				System.out.println(urlString);
				// download.getpic(urlString.toString(), "D:/WeiBo_image" + "/" + uid + "/" +
				// itemId);
			}
		} catch (IOException e) {
			System.out.println("丢了个错误----------");
			e.printStackTrace();
		}
	}
	/**
	 *获取用户图片列表
	 * @return 
	 */
	public List<Weibo> getuserAllPic(int i) {
		//https://weibo.cn/u/7228303285?filter=2&page=1
		String url="https://weibo.cn/u/"+uid+"?filter=2&page="+i;
		Document doc=null;
		String id=null;//微博id
		String ctt=null;//微博内容
		List<Weibo> mList =new ArrayList();
		try {
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			Elements divs=doc.select("div[class=c]");
			System.out.println(doc.toString());
			for (Element element : divs) {
				id=element.attr("id");
				id=id.substring(id.indexOf("_")+1);
				ctt=element.getElementsByClass("ctt").text().toString();
				Weibo wb=new Weibo(id, ctt);
				mList.add(wb);
				//System.out.println(element.getElementsByClass("ctt").text().toString()+id);
				//System.out.println(id);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;

	}
	/**
	 *获取用户微博列表
	 * @return 
	 */
	public List<Weibo> getuserAllweiboid(int i) {
		//https://weibo.cn/u/6837288378?filter=0
		//https://weibo.cn/u/7228303285?filter=2&page=1
		String url="https://weibo.cn/u/"+uid+"?filter=0&page="+i;
		Document doc=null;
		String id=null;//微博id
		String ctt=null;//微博内容
		List<Weibo> mList =new ArrayList();
		try {
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			Elements divs=doc.select("div[class=c]");
			for (Element element : divs) {
				id=element.attr("id");
				id=id.substring(id.indexOf("_")+1);
				ctt=element.getElementsByClass("ctt").text().toString();
				Weibo wb=new Weibo(id, ctt);
				if (ctt.indexOf("的微博视频")>0) {
					id=element.getElementsMatchingText("的微博视频").attr("href").toString();
					wb.setIsvideo(true);
					wb.setItemid(id);
				}
				mList.add(wb);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;
		
	}
	
	/**
	 * 传入的微博id获得原图链接
	 * */
	public void getpic(String id,String title) throws IOException {
		String url="https://weibo.cn/mblog/picAll/"+id+"?rl=1";
		Document doc=null;
//		File file = new File("D:/weibo/data.txt");
//		if (!file.exists()) {
//			file.createNewFile();
//		}
		//FileWriter writer = new FileWriter(file,true);
		//StringBuffer sb=new StringBuffer();
		//sb.append("\n"+title+"\n");
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			Elements elements = doc.getElementsByTag("a");
			for (Element element : elements) {
				String hrefString=element.attr("href").toString();
				if (hrefString.startsWith("/mblog/oripic?")) {
					String urlString="https://weibo.cn"+hrefString;
							System.out.println(urlString);
							downlopic(urlString, title);
					//sb.append("https://weibo.cn"+hrefString+"\n");
				}
			}
			//System.out.println(sb.toString());
//			writer.append(sb);
//			writer.flush();
//			writer.close();
		
	}
	public int getPicPageMaxPage() {
		String url="https://weibo.cn/u/"+uid+"?filter=2&page=1";
		Document doc=null;
			try {
				doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
						.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
						.header("Content-Type", "application/json;charset=UTF-8")
						.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
						.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			String numString=doc.getElementsMatchingOwnText("1/").text().toString();
			numString=numString.substring(numString.indexOf("1/")+2,numString.indexOf("页",3));
			int maxmun=Integer.parseInt(numString);
			return maxmun;
	}
	private void downlopic(String urlString,String title) {
		if (title.length()>10) {
			title=title.substring(0,10)+"_";
		}
		try {
			URL url=new URL(urlString);
			 URLConnection connection = url.openConnection();
			 connection.setRequestProperty("authority","weibo.cn");
			 connection.setRequestProperty("accept", ": text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
			 connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
			 connection.setRequestProperty("accept-language", "zh,zh-CN;q=0.9,en;q=0.8");
			 connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36");
			 connection.setRequestProperty("Cookie", Cookie);
			 connection.connect();
			 Map<String, List<String>> map = connection.getHeaderFields();
				// System.out.println(map.get("Location").get(0));
			downloadUserPic.getpic(map.get("Location").get(0), "D:/weibo/"+uid+"/img",title);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void downloadVideoByUrl(String url) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("--save-page-as-mhtml");
		//System.setProperty("webdriver.chrome.driver","C:\\driver\\chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.get(url);
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
		download.getvideo(videourl, "D:/weibo/"+uid+"/video");
		driver.close();
		System.out.println("driver关闭");
	}
	public String getViedoUrl(String url) throws InterruptedException {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.silentOutput", "true");
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);//屏蔽日志
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("--save-page-as-mhtml");
		//System.setProperty("webdriver.chrome.driver","C:\\driver\\chromedriver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		driver.get(url);
		Thread.sleep(500);
		String htmlString=driver.getPageSource();
		Document document=Jsoup.parse(htmlString);
		Elements videos=document.select("video[id=vjs_video_3_html5_api]");
		String videourl="";
		for (Element video : videos) {
			videourl=video.attr("src");
			videourl=videourl.replace("&amp;", "&");
		}
		driver.close();
		return videourl;
	}
}
