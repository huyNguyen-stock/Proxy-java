package proxy.main;

import java.io.IOException;

import proxy.proxy.Proxy;

public class Main {
	public static void main(String[] args) throws IOException {
		Proxy proxy = new Proxy();
		proxy.acceptRequest();
	}
}
