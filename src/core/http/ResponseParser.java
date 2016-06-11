package core.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResponseParser {
	private BufferedReader in;
	public static final Pattern re_header = Pattern.compile("(.+?):(.*)");
	public static final Pattern re_first = Pattern.compile("(HTTP/1.[0-1]) ([0-9]+) (.+?)$");

	public ResponseParser(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
	}

	public Response nextValue() throws IOException {
		return this.parse_response();
	}

	private Response parse_response() throws IOException {
		Matcher top = re_first.matcher(this.in.readLine());
		top.find();
		int status = Integer.valueOf(top.group(2));
		Map<String, String> headers = parse_header();
		String proto = top.group(1);
		String body = parse_body(headers);
		return new Response(body, status, headers, proto);
	}

	private String parse_body(Map<String, String> headers) throws IOException {
		// Parse body
		LinkedList<Character> body = new LinkedList<Character>();
		String content_length_value = headers.get("content-length");
		if (content_length_value != null) {
			int content_length = Integer.parseInt(content_length_value.trim());
			for (int i = 0; i < content_length; i++) {
				body.add((char) in.read());
			}
			;
		}
		StringBuilder builder = new StringBuilder(body.size());
		for (Character ch : body) {
			builder.append(ch);
		}
		return builder.toString();
	}

	private Map<String, String> parse_header() throws IOException {
		Map<String, String> headers = new HashMap<String, String>();
		String line;

		while ((line = in.readLine()) != null && !line.equals("")) {
			Matcher matcher = ResponseParser.re_header.matcher(line);
			if (matcher.find()) {
				headers.put(matcher.group(1).trim().toLowerCase(), matcher.group(2).trim());

			}
		}
		;
		return headers;
	}
}
