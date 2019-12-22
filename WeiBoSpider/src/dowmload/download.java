package dowmload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class download {
	public static byte[] readInputStream(InputStream inStream) throws Exception,OutOfMemoryError {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[204800];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		try {
			while ((len = inStream.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
		} catch (OutOfMemoryError e) {
			// TODO: handle exception
			System.out.println("OutOfMemoryError,文件过大,无法下载(可能是大于一小时的视频)----------");
			return null;
		}
		outStream.flush();
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}
	public static void getpic(String src, String patch) {
		// TODO Auto-generated method stub
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
			// src=src.replace("\\", "/");
			URL url = new URL(src);
			// URL url= new URL(null, url, new sun.net.www.protocol.https.Handler());
			// 打开连接
			// HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置请求方式
			// connection.setRequestMethod("get");
			// 设置超时响应时间
			int timeout = 5000;
			connection.setConnectTimeout(timeout);
			// 通过输入流获取图片数据
			InputStream inStream = connection.getInputStream();
			// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
			byte[] data;
			try {
				data = readInputStream(inStream);
				FileInit(patch);
				// new一个文件对象用来保存图片，保存在path
				File imageFile = new File(patch + "/" + dateFormat.format(date) + (int) Math.random() * 10000 + ".jpg");
				// 如果文件存在就删除
				if (imageFile.exists())
					imageFile.delete();
				// 创建输出流
				FileOutputStream outStream = new FileOutputStream(imageFile);
				// 写入数据
				outStream.write(data);
				// 关闭输出流
				outStream.close();
				System.out.println("下载完成，文件位于" + imageFile.getPath());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static void getvideo(String src, String patch) {
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int timeout = 5000;
			connection.setConnectTimeout(timeout);
			InputStream inStream = connection.getInputStream();
			byte[] data;
				data = readInputStream(inStream);
				System.out.println(data == null);
				if(data==null) {
					String txtpath=patch+"/无法下载的视频.txt";
					System.out.println(txtpath);
					FileInit(patch);
					File file=new File(txtpath);
					if (!file.exists()) {
						file.createNewFile();
					}
					return;
					}
			FileInit(patch);
			// new一个文件对象用来保存图片，保存在path
			File imageFile = new File(patch + "/" + dateFormat.format(date) + (int) Math.random() * 10000 + ".mp4");
			// 如果文件存在就删除
			if (imageFile.exists())
				imageFile.delete();
			// 创建输出流
			FileOutputStream outStream = new FileOutputStream(imageFile);
			// 写入数据
			outStream.write(data);
			// 关闭输出流
			outStream.close();
			System.out.println("下载完成，文件位于" + imageFile.getPath());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void FileInit(String patch) {
		File file = new File(patch);
		if (file.exists()) {
			if (file.isDirectory()) {
			} else {
			}
		} else {
			file.mkdirs();
		}
	}

	public static void main(String[] args) {
		// http://f.video.weibocdn.com/003lnNR7lx07xJK9ML7O0104120bkIXX0E040.mp4?label=mp4_hd&template=640x360.25.0&trans_finger=62b30a3f061b162e421008955c73f536&Expires=1571039959&ssig=lM%2BSTDSQZQ&KID=unistore,video
		download.getvideo(
				"http://f.video.weibocdn.com/003lnNR7lx07xJK9ML7O0104120bkIXX0E040.mp4?label=mp4_hd&template=640x360.25.0&trans_finger=62b30a3f061b162e421008955c73f536&Expires=1571039959&ssig=lM%2BSTDSQZQ&KID=unistore,video",
				"C:\\weibo\\a.mp4");
	}
}
