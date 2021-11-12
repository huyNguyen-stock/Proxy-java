package proxy.util;

public class Util {
	public static String Forbidden = "HTTP/1.1 403 Forbidden\r\n\r\n<HTML>\r\n<BODY>\r\n<H1>403 Forbidden</H1>\r\n<H2>You don't have permission to access this server</H2>\r\n</BODY></HTML>\r\n";
	public static String HOST = "host";
	public static String METHOD = "method";
	public static String URL = "url";
	public static String DATA = "data";
	public static String POST = "POST";
	public static String GET = "GET";
}
