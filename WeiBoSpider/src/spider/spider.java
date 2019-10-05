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

import javax.naming.spi.DirStateFactory.Result;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
	 * 获取微博平路里的图片
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
			//System.out.println(doc.toString());
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
	 * 获取最大的评论页数，一页十条
	 * */
	private int getmaxPagenumber() {
		String url = "https://weibo.cn/comment/hot/" + itemId + "?uid=" + uid + "&rl=0&gid=10001#cmtfrm";
		Document doc = null;
		int num = 0;
		try {
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// method="post"
		Elements txt = doc.select("form[method=post]");
		for (Element element : txt) {
			String text = element.text().toString();
			if (text.endsWith("页")) {
				text = text.substring(text.indexOf("/") + 1, text.indexOf("页", 3));
				num = Integer.parseInt(text);
			}
		}
		return num;
	}
	/**
	 * 获取评论的文本
	 * */
	private void getctt(int i) {

		Document doc;
		try {
			String url;
			// 判断翻页
			if (i > 1) {
				url = "https://weibo.cn/comment/hot/" + itemId + "?uid=" + uid + "&rl=0&gid=10001&page=" + i;
			} else {
				url = "https://weibo.cn/comment/" + itemId + "?uid=" + uid + "&rl=0&gid=10001#cmtfrm";
			}
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Content-Type", "application/json;charset=UTF-8")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.header("Cookie", Cookie).ignoreContentType(true).data().timeout(10000).get();
			Elements contents = doc.select("span[class=ctt]");
			for (Element element : contents) {
				String ctt = element.text().toString();
				System.out.println(ctt);
			}
		} catch (IOException e) {
			System.out.println("丢了个错误----------");
			e.printStackTrace();
		}

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
	/**
	 * 获取评论里的图片哒
	 * */
	public static void _main(String[] args) {
		String Cookie = "";
		String itemId = "";
		String uid = "";
		spider spider = new spider(Cookie, itemId, uid);
		//int max = spider.getmaxPagenumber();
		for (int i = 1; i < 195; i++) {
			try {
				System.out.println("正在获取第" + i + "页评论," + "共有" + 195 + "页");
				spider.getuserAllPic(i);
				System.out.println("获取中。。。。。");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("已完成,保存在D:/weibo/data.doc");
	}
	/**
	 * 获取评论里的图片哒
	 * */
	public static void __main(String[] args) {
		String Cookie = "";
		String itemId = "";
		String uid = "";
		spider spider = new spider(Cookie, itemId, uid);
		for (int i = 1; i < 195; i++) {
			try {
				System.out.println("正在获取第" + i + "页评论," + "共有" + 195 + "页");
				spider.getuserAllPic(i);
				System.out.println("获取中。。。。。");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("已完成,保存在D:/weibo/data.doc");
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
			downloadUserPic.getpic(map.get("Location").get(0), "D:/weibo/"+uid+"img",title);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
