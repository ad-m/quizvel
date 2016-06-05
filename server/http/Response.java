/* 
HTTP/1.1 200 OK
Date: Mon, 23 May 2005 22:38:34 GMT
Content-Type: text/html; charset=UTF-8
Content-Encoding: UTF-8
Content-Length: 138
Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT
Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)
ETag: "3f80f-1b6-3e1cb03b"
Accept-Ranges: bytes
Connection: close

<html>
<head>
  <title>An Example Page</title>
</head>
<body>
  Hello World, this is a very simple HTML document.
</body>
</html>
*/
package http;

import java.util.HashMap;
import java.util.Map;

public class Response {
	public String proto;
	public int status;
	private Map<String, String> headers = new HashMap<String, String>();
	private String body;
	public Response(String body, int status, Map<String, String> headers,
			String proto) {
		this.proto = proto;
		this.status = status;
		this.headers = headers;
		this.body = body;
	}

	public Response(String body, int status,
			Map<String, String> headers) {
		this(body, status, headers, "HTTP/1.1");
	}

	public Response(String body, int status) {
		this(body, status, new HashMap<String, String>());
	}
	public Response(String body) {
		this(body, 200);
	}
	
	
	private String status_label(){
		if(this.status == 200){
			return "OK";
		} else if(this.status == 401){
			return "Unauthorized status";
		}
		return "Unknown";
	}
	
	public String toString(){
		StringBuffer content = new StringBuffer(this.proto);		
		content.append(" ");
		content.append(status);
		content.append(" ");
		content.append(this.status_label());
		content.append("\n");
		for (Map.Entry<String, String> entry : this.headers.entrySet()) {
			content.append(entry.getKey());
			content.append(": ");
		    content.append(entry.getValue());
		    content.append("\n");
		}
		content.append("Content-Length: " + this.body.length());
		content.append("\n\n");
		content.append(this.body);
		return content.toString();
	}
	public static void main(String[] args) {
		System.out.print(new Response("OK", 200, new HashMap<String, String>()));
		
	}
	
	
}
