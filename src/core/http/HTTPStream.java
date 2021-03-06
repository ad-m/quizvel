package core.http;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * dekorator odpowiedzialny za transmisje obiektów HTTP przez gniazdo
 * 
 * @author adas
 *
 */
public class HTTPStream implements Closeable {
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	private RequestParser request_parser;
	private ResponseParser response_parser;

	public HTTPStream(Socket socket) throws IOException {
		this.socket = socket;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
		this.request_parser = new RequestParser(this.in);
		this.response_parser = new ResponseParser(this.in);
	};

	/**
	 * @return odczytanie żadania z gniazda
	 * @throws IOException
	 */
	public Request readRequest() throws IOException {
		Request request = this.request_parser.nextValue();
		System.err.println(request);
		return request;
	}

	/**
	 * @return odczytanie odpowiedzi z gniazda
	 * @throws IOException
	 */
	public Response readResponse() throws IOException {
		Response response = this.response_parser.nextValue();
		System.err.println(response);
		return response;
	}

	/**
	 * @param request
	 *            przesłanie obiektu HTTP
	 * @throws IOException
	 *             problemy komunikacyjne z serwere
	 */
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
