package client.dao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import client.core.Config;
import core.http.HTTPObject;
import core.http.HTTPStream;
import core.http.Request;
import core.http.Response;
import core.http.ResponseParser;

public class AbstractDAO {

	public AbstractDAO() {
		super();
	}

	public Response send_request(Request request, User user) throws UnknownHostException, IOException {
		;
		Response response = null;
		try (HTTPStream http = new HTTPStream(new Socket(Config.SERVER_HOST, Config.SERVER_PORT))) {
			if (user != null) {
				request.setUser(user.getUsername(), user.getPassword());
			}
			http.write(request);
			response = http.readResponse();
		}
		return response;
	}

	public HTTPObject send_request(Request request) throws UnknownHostException, IOException {
		return send_request(request, null);
	}

}