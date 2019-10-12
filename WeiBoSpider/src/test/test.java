package test;

import java.io.IOException;
import java.util.List;

import spider.spider;
import weibo.Weibo;

public class test {
	public static void main(String[] args) {
		String Cookie = "";
		String itemId = "";
		String uid = "";
		spider spider = new spider(Cookie, itemId, uid);
		int max =spider.getPicPageMaxPage();
		for (int i = 1; i <= max; i++) {
			List<Weibo> list=spider.getuserAllPic(i);
			for (Weibo weibo : list) {
				try {
					spider.getpic(weibo.getItemid(),weibo.getContent());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				System.out.println("第"+i+"页ok,剩余"+(max-i)+"页");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("完成");
	}

}
