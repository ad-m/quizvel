package client.dao;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.core.Config;
import core.http.HTTPStream;
import core.http.Request;
import core.http.Response;

/**
 * Klasa abstrakcyjna do komunikacji z serwerem z wykorzystaniem HTTP.
 * 
 * @author adas
 *
 */
public class AbstractDAO {

	public Response send(Request request, DAOUser user)
			throws UnknownHostException, IOException, ServerErrorException {
		Response response = null;
		try (HTTPStream http = new HTTPStream(new Socket(Config.SERVER_HOST, Config.SERVER_PORT))) {
			if (user != null) {
				request.setUser(user.getUsername(), user.getPassword());
			}
			http.write(request);
			response = http.readResponse();
		}
		if (response.getStatus() == 500) {
			throw new ServerErrorException();
		}
		return response;
	}

	public Response send(Request request) throws UnknownHostException, IOException, ServerErrorException {
		return send(request, null);
	}

}