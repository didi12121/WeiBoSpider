package test;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import spider.spider;
import weibo.Weibo;

public class test {
	public static void main(String[] args) {
		try {
			Document document=Jsoup.connect("http://39.105.223.251/getday").ignoreContentType(true).get();
			String string=document.getElementsByTag("body").text().toString();
			JSONArray ja=JSONArray.parseArray(string);
			System.out.println(ja.getJSONObject(1).getString("content"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
				
	}

}
