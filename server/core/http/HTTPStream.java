package core.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HTTPStream {
	Socket socket;
	InputStream in;
	OutputStream out;
	public HTTPStream(Socket socket) throws IOException{
		this.socket = socket;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
	};
	public Request readRequest() throws IOException{
		return new RequestParser(this.in).nextValue();
	}
	public Response readResponse() throws IOException{
		return new ResponseParser(this.in).nextValue();
	}

	public void write(HTTPObject request) throws IOException{
		this.out.write(request.getBytes());
		this.out.flush();
	}
	public void close() throws IOException{
		this.in.close();
		this.out.close();
		this.socket.close();
	}
}
