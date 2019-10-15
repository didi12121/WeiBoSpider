package main;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import spider.spider;
import weibo.Weibo;

public class run {

	public static void main(String[] args) throws InterruptedException, IOException {
		String Cookie = "ALF=1572627995; SCF=Ag_W3v84MPYLoPD8wHuy7VL3JmYSgKZdSa_JB6cLrwu95nF8ZBn7VWqsmtsHq6WorcFsOLAlN0HaR6BmL0ypmB0.; SUB=_2A25wkKlMDeRhGedG6lAT9ibEzj6IHXVQejcErDV6PUJbktANLVrzkW1NUWcmyCeOymAbqxy3J9LbL0ZCxaOhLfNK; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFieR0TFBa2Uo5-7fns3LXM5JpX5KzhUgL.Fo2ReKzESonRSKz2dJLoIceLxKqLBoeLBoMLxKML12qLBK5LxK-LBK-LBoqLxKMLB.zLB.qLxK-LBo5L1K2LxKMLBoeL1KqLxK.LBKeL1--LxK-L1hqLBo5LxK-L122LBK5LxKnL122L12zLxK-L1-eL1hqt; SUHB=0M2EKPaTGHbXTD; _T_WM=1669403a0ba4da10ad219c4b193c352f";
		String itemId = "";
		String uid = "7228303285";
		System.out.println("aaaaa----");
		spider spider = new spider(Cookie, itemId, uid);
		int max =spider.getPicPageMaxPage();
		Thread.sleep(5000);
		for (int i = 1; i <= max; i++) {
			List<Weibo> list=spider.getuserAllweiboid(i);
			Thread.sleep(5000);
			for (Weibo weibo : list) {
				//System.out.printf(weibo.getItemid()+""+weibo.getContent()+""+weibo.isIsvideo()+"-----\t\n");
				if (weibo.isIsvideo()) {
					//System.out.println(weibo.getItemid()+weibo.getContent());
					System.out.println("准备开始下载");
					Thread.sleep(5000);
					spider.downloadVideoByUrl(weibo.getItemid());
				}else {
					Thread.sleep(5000);
					try {
						spider.getpic(weibo.getItemid(),weibo.getContent());
					} catch (NullPointerException e) {
						// TODO: handle exception
						e.printStackTrace();
						continue;
					} catch(SocketTimeoutException e){
						e.printStackTrace();
						continue;
					} catch(OutOfMemoryError e){
						System.out.println("内存溢出");
						continue;
					}finally {
						
					}
				}
				Thread.sleep(5000);
				weibo=null;
			}
			System.out.println("第"+i+"页ok,剩余"+(max-i)+"页");
			Thread.sleep(5000);
		}
	}

}
