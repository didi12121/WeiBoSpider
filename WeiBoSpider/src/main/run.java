package main;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import dowmload.downloadWithThunder;
import spider.spider;
import weibo.Weibo;

public class run {

	public static void main(String[] args) throws InterruptedException, IOException {
		boolean useThunder=false;//是否使用迅雷下载
		String Cookie = " _T_WM=65feccd20587655db526c10977789b47; SUB=_2A25w5vlBDeRhGedG6lAT9ibEzj6IHXVQKIcJrDV6PUJbkdANLUP5kW1NUWcmyDyL2KPAm9rT9Y2xlQGS8SMYrihq; SUHB=0P1NJFjxGS9TAc; SCF=Asibdzh4nvOqJ88iAMMg-P6ItWz1yW0nXvzuFcfvvZqhoOmeuNVAs5WTkXqnGHzTBtWOtYXFXU5VrSqTJWhU16s.";
		String itemId = "";
		String uid = "7228303285";
		System.out.println("开始下载--");
		spider spider = new spider(Cookie, itemId, uid);
		int max =spider.getPicPageMaxPage();
		Thread.sleep(1000);
		for (int i = 1; i <= max; i++) {
			List<Weibo> list=spider.getuserAllweiboid(i);
			for (Weibo weibo : list) {
				if (weibo.isIsvideo()) {
					System.out.println("准备开始下载");
					Thread.sleep(1000);
					if (useThunder) {//调用迅雷下载，传入ThunderStart.exe的路径
						downloadWithThunder.start("D:/Program Files (x86)/Thunder Network/Thunder/Program/ThunderStart.exe",spider.getViedoUrl(weibo.getItemid()));
					}else {
						spider.downloadVideoByUrl(weibo.getItemid());
					}
				}else {
					Thread.sleep(1000);
					try {
						spider.getpicurl(weibo.getItemid(),weibo.getContent());
					} catch (NullPointerException e) {
						e.printStackTrace();
						continue;
					} catch(SocketTimeoutException e){
						e.printStackTrace();
						continue;
					}
				}
				Thread.sleep(4000);
				weibo=null;
			}	
			System.out.println("第"+i+"页ok,剩余"+(max-i)+"页");
			Thread.sleep(5000);
		}
	}

}
