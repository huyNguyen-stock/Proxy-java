package proxy.model;

public class WebInfor {
	private String host;
	private String url;
	private int port;
	private String method;
	private String data;
	
	public WebInfor(String host, String url, int port, String data, String method) {
		super();
		this.host = host;
		this.url = url;
		this.port = port;
		this.method = method;
		this.data = data;
	}
	public WebInfor() {
		super();
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
}
