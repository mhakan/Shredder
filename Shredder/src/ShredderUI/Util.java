package ShredderUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Util {

	private Util() {
	}
	
	

	public static <T> List<T> GetExec(String param) {

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		List<String> lst = new ArrayList<String>();

		try {
			while ((line = br.readLine()) != null) {
				 
				lst.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (List<T>) lst;
	}

}
