package main;

import java.io.IOException;
import java.util.List;

import spider.spider;
import weibo.Weibo;

public class run {

	public static void main(String[] args) throws InterruptedException, IOException {
		String Cookie = "";
		String itemId = "";
		String uid = "";
		System.out.println("aaaaa----");
		spider spider = new spider(Cookie, itemId, uid);
		int max =spider.getPicPageMaxPage();
		for (int i = 1; i <= max; i++) {
			List<Weibo> list=spider.getuserAllweiboid(1);
			for (Weibo weibo : list) {
				if (weibo.isIsvideo()) {
					System.out.println(weibo.getItemid()+weibo.getContent());
					spider.downloadVideoByUrl(weibo.getItemid());
				}else {
					spider.getpic(weibo.getItemid(),weibo.getContent());
				}
			}
			System.out.println("第"+i+"页ok,剩余"+(max-i)+"页");
			Thread.sleep(5000);
		}
	}

}
