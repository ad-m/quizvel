package server.core;

import java.io.IOException;
import java.net.Socket;

import core.http.HTTPStream;
import core.http.Request;
import core.http.Response;
import server.router.Router;
import server.view.generic.AbstractView;

/**
 * Klasa wątku obsługi żadania do serwera
 * 
 * @author adas
 */
public class ClientThread extends Thread {

	private Socket socket;
	private HTTPStream http;

	public ClientThread(Socket socket) throws IOException {
		this.socket = socket;
		this.http = new HTTPStream(this.socket);
	}

	public void run() {
		try {
			Request request = this.http.readRequest();
			if (request == null) {
				http.write(new Response("Malformed request", 500));
				return;
			}

			AbstractView view = Router.getInstance().find(request);
			if (view == null) {
				http.write(new Response("Page not found", 404));
				return;
			}
			Response response = null;
			try {
				response = view.getResponse(request);
			} catch (Exception e) {
				response = new Response("Exception occur", 500);
				e.printStackTrace();
			}
			http.write(response);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}