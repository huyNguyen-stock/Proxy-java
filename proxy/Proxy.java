package proxy.proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import proxy.thread.SocketThread;

public class Proxy {
	private ServerSocket serverProxy;

	public Proxy() {
	}

	public Proxy(ServerSocket serverProxy) {
		this.serverProxy = serverProxy;
	}

	public ServerSocket getServerProxy() {
		return serverProxy;
	}

	public void setServerProxy(ServerSocket serverProxy) {
		this.serverProxy = serverProxy;
	}
	
	//Tao socket va chap nhan ket noi
	public void acceptRequest() {
		try {
			//Khoi tao server voi port 8888
			this.serverProxy = new ServerSocket(8888);
			System.out.println("Proxy server start!");
			
			//Lien tuc chap nhan ket noi toi Proxy Server va xu ly
			while (true) {
				Socket s = this.serverProxy.accept();
				new SocketThread(s).start();
			}
		} catch (IOException e) {
		}
	}
}
