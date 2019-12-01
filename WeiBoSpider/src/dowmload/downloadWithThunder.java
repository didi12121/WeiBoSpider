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
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
