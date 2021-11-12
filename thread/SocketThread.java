package proxy.thread;

import java.net.Socket;

import proxy.http.Http;

public class SocketThread extends Thread {
	private Socket socket = null;

	@Override
	public void run() {
			try {
				//Lay response ve cho socket voi request duoc gui toi
				(new Http()).getReponse(this.socket);
			} catch (Exception e) { } 
	}

	public SocketThread() {
	}

	public SocketThread(Socket s) {
		this.socket = s;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setQueueSocket(Socket s) {
		this.socket = s;
	}

}
