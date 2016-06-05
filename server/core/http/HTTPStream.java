package core.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPStream implements Closeable {
	Socket socket;
	InputStream in;
	OutputStream out;
	RequestParser request_parser;
	ResponseParser response_parser;

	public HTTPStream(Socket socket) throws IOException {
		this.socket = socket;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
		this.request_parser = new RequestParser(this.in);
		this.response_parser = new ResponseParser(this.in);
	};

	public Request readRequest() throws IOException {
		Request request = this.request_parser.nextValue();
		System.err.println(request);
		return request;
	}

	public Response readResponse() throws IOException {
		Response response = this.response_parser.nextValue();
		System.err.println(response);
		return response;
	}

	public void write(HTTPObject request) throws IOException {
		this.out.write(request.getBytes());
		System.err.println(request);
		this.out.flush();
	}

	public void close() throws IOException {
		this.in.close();
		this.out.close();
		this.socket.close();
	}
}
