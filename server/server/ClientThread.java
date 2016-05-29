package server;

import http.Request;
import http.RequestParser;
import http.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import router.Router;
import view.AbstractView;

class ClientThread extends Thread{
	
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	ClientThread(Socket socket)throws IOException{
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void run(){
		try {
			process();
			out.flush();
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void process() throws IOException {
		Request request = new RequestParser(this.in).nextValue();
		if(request == null){
			out.print("Malformed request");
			return;
		};

		Router router = Router.getInstance();
		
		AbstractView view = router.find(request);
		if(view == null){
			out.print(new Response("Page not found", 404));;
			return;
		};

		out.print(view.getResponse(request).toString());
	}
}