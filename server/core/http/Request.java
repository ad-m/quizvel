package core.http;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Request implements HTTPObject {
	public enum Method {
		POST, GET, DELETE, PUT
	}

	public Method method;
	public String url;
	public String proto;
	public HashMap<String, String> headers = new HashMap<String, String>();
	public String body;

	public Request(String url, String method, String body, HashMap<String, String> headers, String proto) {
		this.method = Method.valueOf(method);
		if (this.method == null) {
			this.method = Method.GET;
		}
		this.url = url;
		this.proto = proto;
		this.headers = headers;
		this.body = body;
	}

	public Request(String url, String method, String body, HashMap<String, String> headers) {
		this(url, method, body, headers, "HTTP/1.1");
	}

	public Request(String url, String method, String body) {
		this(url, method, body, new HashMap<String, String>());
	}

	public Request(String url, String method) {
		this(url, method, "");
	}

	public void setUser(String username, String password) {
		String encoded = get_auth_string(username, password);
		this.headers.put("Authorization", encoded);
	}

	private static String get_auth_string(String username, String password) {
		return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	public Method getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	public String getProto() {
		return proto;
	}

	public String get(Object key) {
		return headers.get(key);
	}

	@Override
	public String toString() {
		return "HTTPRequest [method=" + method + ", url=" + url + "]";
	}
	
	public byte[] getBytes(){
		StringBuffer content = new StringBuffer();		
		content.append(this.method);
		content.append(" ");
		content.append(this.url);
		content.append(" ");
		content.append(this.proto);
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
		System.out.print(content.toString());
		return content.toString().getBytes();
	}

	public JSONObject getJSON() throws JSONException {
		return (JSONObject) new JSONTokener(this.body).nextValue();
	}

	public static void main(String[] args) {
		assert Request.get_auth_string("xyz", "xyz").equals("Basic eHl6Onh5eg==");
	}
}
