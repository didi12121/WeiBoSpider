package dowmload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class downloadWithThunder {
	public static void start(String ThunderPath,String urlString) throws IOException {
		Runtime rt = Runtime.getRuntime();
		try {
			System.out.println(ThunderPath + " -pURL " + urlString);
			Process p = rt.exec(ThunderPath + " -pURL " + urlString);
			// p.destroy();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		File iniFile =new File("init.txt");
		if (!iniFile.exists()) {
			FileReader reader = new FileReader(iniFile);
			char[] a = new char[reader.read()];
			reader.read(a);
			String string="";
			for (char c : a) {
				string+=c;
			}
			System.out.println(string.trim());
		}else {
			iniFile.createNewFile();
			File file = new File("C:\\");
			System.out.println("正在搜索迅雷路径");
			String path=Filea(file);
			if (path==null) {
				System.out.println("正在搜索迅雷路...");
				File file2 = new File("D:\\");
				path=Filea(file);
			}
			System.out.println(path+"----");
		}
		
	}

	private static String Filea(File file) throws IOException {
		String pathString = null;
		if (file.isDirectory()) {
			File[] filelist = file.listFiles();
			for (File file2 : filelist) {
				try {
					Filea(file2);
				} catch (NullPointerException e) {
					continue;
				}
			}
		} else {
			String name=file.getName();
			if (name.equals("ThunderStart.exe")) {
				pathString=file.getAbsolutePath();
			}
		}
		return pathString;
	}
}
