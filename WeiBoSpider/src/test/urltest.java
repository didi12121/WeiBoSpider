package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import dowmload.download;

public class urltest {
	
	public static void main(String[] args) {
		String urlString="https://weibo.cn/mblog/oripic?id=HxUDuwKer&u=99706441gy1g3tfxykquxj21ac1psh8e&rl=1";
		String Cookie = "_T_WM=92697636814; ALF=1572512910; SCF=AgCC-Sxlratg_LMnVwYk9b_cQyCnFa1U2Nbho8mmISEwsTb-awA1xHMTXoQe06A_VYqyyLnpUKdpsAVbIaZEfNY.; SUB=_2A25wl2ffDeRhGedG6lAT9ibEzj6IHXVQeAmXrDV6PUJbktANLXXikW1NUWcmyD8u25b0GnoFC9SjzmK6WdAYZ1zC; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFieR0TFBa2Uo5-7fns3LXM5JpX5KzhUgL.Fo2ReKzESonRSKz2dJLoIceLxKqLBoeLBoMLxKML12qLBK5LxK-LBK-LBoqLxKMLB.zLB.qLxK-LBo5L1K2LxKMLBoeL1KqLxK.LBKeL1--LxK-L1hqLBo5LxK-L122LBK5LxKnL122L12zLxK-L1-eL1hqt; SUHB=0u5QppCBXZxPkL";
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
				 System.out.println(map.get("Location").get(0));
			download.getpic(map.get("Location").get(0), "D:/weibo/a");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
