package proxy.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import proxy.file.FileUtil;
import proxy.model.WebInfor;
import proxy.util.Util;

public class Http {
	
	public void getReponse(Socket clientSocket) {
		try {
			//client <- proxy: response duoc gui ve client bang outClient
			OutputStream outClient = clientSocket.getOutputStream();
			//client -> proxy: truyen request tu client vao inputClient
			BufferedReader inputClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			//Chuyen request sang StringBuilder
			StringBuilder requestSB = new StringBuilder();
			char[] buffer = new char[1024];
			int length = 0;
			while (true) {
				int l = inputClient.read(buffer);
				requestSB.append(buffer);
				length += l;
				if (l < 1024) {
					break;
				}
			}
			//Chuyen request sang String
			String request = requestSB.toString().substring(0, length);
			
			//Phan tach thong tin cua request
			WebInfor Info = getInfoRequest(request);
			
			//Chi xu ly request co port = 80
			if (Info.getPort() == 80)
			{
				FileUtil fileUtil = new FileUtil();
				
				//Dia chi host cua request khong nam trong blacklist.txt
				if (!(fileUtil.inBlackList(Info.getHost()))) {
					
					//GET method: in url
					if (Info.getMethod().equals(Util.GET)) {
						System.out.println("GET: " + Info.getUrl());
					}
					//POST method: in url + data
					else if (Info.getMethod().equals(Util.POST)) {
						System.out.println("POST: " + Info.getUrl());
						System.out.println("      " + Info.getData());
					}
					//other method: khong xu ly
					else {
						clientSocket.close();
						return;
					}
					//Gui request toi Web Server roi lay response va tra ve client
					HTTP_Request(request, Info, outClient);
				}
				//Dia chi host bi chan
				else {
					//Tra ve 403 Forbidden
					clientSocket.getOutputStream().write(Util.Forbidden.getBytes());
					outClient.flush();
					System.out.println("Web is blocked! " + "Host: " + Info.getHost());
				}
			}
		} catch (IOException e) {
		} finally {
			try {
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
			}
		}
	}

	private WebInfor getInfoRequest(String string) throws IOException {
		
		WebInfor webInfor = new WebInfor();
		//Phan tach request: method, host, port, url, data
		if (!string.equals("")) {
			String str = string.substring(0, string.indexOf("\r\n"));
			String[] list = str.split(" ");
			webInfor.setMethod(list[0]);
			webInfor.setUrl(list[1]);
			string = string.substring(string.indexOf("\r\n") + 2);
			str = string.substring(0, string.indexOf("\r\n"));
			list = str.split(" ");
			list = list[1].split(":");
			webInfor.setHost(list[0]);
			if (list.length > 1) {
				webInfor.setPort(Integer.parseInt(list[1]));
			} else {
				webInfor.setPort(80);
			}
			if (webInfor.getMethod().equals(Util.POST)) {
				string = string.substring(string.indexOf("\r\n\r\n") + 4);
				webInfor.setData(string);
			}
		}
		return webInfor;
	}

	private void HTTP_Request(String message, WebInfor rqInfor, OutputStream outClient) throws UnknownHostException, IOException {

		//Tao socket cho Proxy Server de gui va nhan du lieu voi Web Server
		Socket socket = new Socket(rqInfor.getHost(), rqInfor.getPort());
		socket.setSoTimeout(2000);
		
		//proxy -> web server
		OutputStream out = socket.getOutputStream();
		//proxy <- web server
		InputStream in = socket.getInputStream();
		
		//Gui request toi Web Server
		out.write(message.getBytes());
		out.flush();
		
		//Nhan response tu Web Server gui cho Proxy Server, dong thoi gui ve cho client (outClient)
		int bytes_read;
		byte[] buffer = new byte[1024];
		while ((bytes_read = in.read(buffer)) != 1) {
			outClient.write(buffer, 0, bytes_read);
			outClient.flush();
		}
		socket.close();
	}
}
