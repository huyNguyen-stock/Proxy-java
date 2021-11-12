package proxy.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class FileUtil {
	private LinkedList<String> listHost;

	//Doc cac dia chi host tu file blacklist.txt
	public void readHostFromFile() {
		File file;
		FileReader fileReader = null;
		BufferedReader br = null;
		this.listHost = new LinkedList<String>();
		try {
			file = new File("blacklist.txt");
			fileReader = new FileReader(file);
			br = new BufferedReader(fileReader);

			String str;
			while ((str = br.readLine()) != null) {
				this.listHost.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean inBlackList(String hostName) {
		//Doc file ra list
		readHostFromFile();
		//Kiem tra hostName ton tai trong list khong
		for (String string : this.listHost) {
			if (string.equals(hostName)) {
				return true;
			}
		}
		return false;
	}
}
