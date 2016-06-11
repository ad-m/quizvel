package core.http;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Klasa reprezentująca obiekt żadania HTTP
 * 
 * @author adas
 *
 */
public class Request implements HTTPObject {

	/**
	 * metoda HTTP
	 * 
	 * @author adas
	 *
	 */
	public enum Method {
		POST, GET, DELETE, PUT
	}

	public Method method;
	public String url;
	public String proto;
	public Map<String, String> headers = new HashMap<String, String>();
	public String body;

	public Request(String url, String method, String body, Map<String, String> headers, String proto) {
		this.method = Method.valueOf(method);
		if (this.method == null) {
			this.method = Method.GET;
		}
		this.url = url;
		this.proto = proto;
		this.headers = headers;
		this.body = body;
	}

	public Request(String url, String method, String body, Map<String, String> headers) {
		this(url, method, body, headers, "HTTP/1.1");
	}

	public Request(String url, String method, String body) {
		this(url, method, body, new HashMap<String, String>());
	}

	public Request(String url, String method, JSONObject json) {
		this(url, method, json.toString(), new HashMap<String, String>());
	}

	public Request(String url, String method) {
		this(url, method, "");
	}

	/**
	 * ustawienie danych autoryzacyjnych żądania
	 * 
	 * @param username
	 *            login użytkownika
	 * @param password
	 *            hasło użytkownika
	 */
	public void setUser(String username, String password) {
		String encoded = get_auth_string(username, password);
		this.headers.put("Authorization", encoded);
	}

	private static String get_auth_string(String username, String password) {
		return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
	}

	/**
	 * @return metoda żądania
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @return adres URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return nagłówki żądania
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.http.HTTPObject#getBody()
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @return wersja protokołu HTTP żądania
	 */
	public String getProto() {
		return proto;
	}

	/**
	 * @param key
	 * @return nagłówek HTTP
	 */
	public String get(Object key) {
		return headers.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HTTPRequest [method=" + method + ", url=" + url + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.http.HTTPObject#getBytes()
	 */
	public byte[] getBytes() {
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
		return content.toString().getBytes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see core.http.HTTPObject#getJSON()
	 */
	public JSONObject getJSON() throws JSONException {
		return (JSONObject) new JSONTokener(this.body).nextValue();
	}

	/**
	 * @param args
	 *            test
	 */
	public static void main(String[] args) {
		assert Request.get_auth_string("xyz", "xyz").equals("Basic eHl6Onh5eg==");
	}
}
