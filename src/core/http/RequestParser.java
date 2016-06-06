package core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {
	private BufferedReader in;
	public static final Pattern re_header = Pattern.compile("(.+?):(.*)");
	public static final Pattern re_first = Pattern.compile("(POST|GET|DELETE) (.+?) (HTTP/1.[0-1])");
	
	
	public RequestParser(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
	}

	public Request nextValue() throws IOException{
		return this.parse_request();
	}
	private Request parse_request() throws IOException {
		Request request = null;
		String line = this.in.readLine();
		if(line == null){
			return null;
		}
		Matcher top = re_first.matcher(line);
		if(top.find()){
			String method = top.group(1);
			String url = top.group(2);
			String proto = top.group(3);
			HashMap<String, String> headers = parse_header();
			String body = parse_body(headers);
			request = new Request(url, method, body, headers, proto);
		};
		return request;
	}

	private String parse_body(HashMap<String, String> headers) throws IOException {
		// Parse body
		LinkedList<Character> body = new LinkedList<Character>();
		String content_length_value = headers.get("content-length");
		if(content_length_value != null){
			int content_length = Integer.parseInt(content_length_value.trim());
			for(int i=0; i<content_length; i++){
				body.add((char) in.read());
			};
		}
		StringBuilder builder = new StringBuilder(body.size());
		for(Character ch: body)
		{
		    builder.append(ch);
		}
		return builder.toString();
	}

	private HashMap<String, String> parse_header() throws IOException {
		HashMap<String, String> headers = new HashMap<String, String>();
		String line;
		
		while((line = in.readLine()) !=null && !line.equals("")){ 
			Matcher matcher = RequestParser.re_header.matcher(line);
			if(matcher.find()){
				headers.put(matcher.group(1).trim().toLowerCase(), matcher.group(2).trim());
				
			}
		};
		return headers;
	}
}
