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
		String Cookie = "";
		String itemId = "";
		String uid = "";
		System.out.println("开始下载--");
		spider spider = new spider(Cookie, itemId, uid);
		int max =spider.getPicPageMaxPage();
		for (int i = 1; i <= max; i++) {
			List<Weibo> list=spider.getuserAllweiboid(i);
			for (Weibo weibo : list) {
				if (weibo.isIsvideo()) {
					System.out.println("准备开始下载");
					if (useThunder) {//调用迅雷下载，传入ThunderStart.exe的路径
						downloadWithThunder.start("D:/Program Files (x86)/Thunder Network/Thunder/Program/ThunderStart.exe",spider.getViedoUrl(weibo.getItemid()));
					}else {
						spider.downloadVideoByUrl(weibo.getItemid());
					}
				}else {
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
			}	
			System.out.println("第"+i+"页ok,剩余"+(max-i)+"页");
		}
	}

}
