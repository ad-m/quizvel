package http;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Request {
	public enum Method {
	    POST,GET,DELETE
	}
	public Method method;
	public String url;
	public String proto;
	public HashMap<String, String> headers = new HashMap<String, String>();
	public String body;
	
	
	public Request(String method, String url, String proto,
			HashMap<String, String> headers, String body) {
		super();
		this.method = Method.valueOf(method);
		if(this.method == null){
			this.method = Method.GET;
		}
		this.url = url;
		this.proto = proto;
		this.headers = headers;
		this.body = body;
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
	public JSONObject getJSON() throws JSONException{
		return (JSONObject) new JSONTokener(this.body).nextValue();
	}
}
