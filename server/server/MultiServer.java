package server;

import http.Request.Method;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import router.Router;
import storage.QuestionStorage;
import storage.UserStorage;
import router.RegexpRoute;
import view.OKView;
import view.QuestionCreateView;
import view.QuestionDeleteView;
import view.QuestionGetView;
import view.QuestionListView;
import view.QuestionUpdateView;
import view.SaveJSONView;
import view.SurveyCheckView;
import view.SurveyGetView;
import view.UserListView;
import view.UserPromoteView;
import view.UserRegisterView;

public class MultiServer {
	public static final int PORT = Config.LISTING_PORT;
	private ServerSocket server;

	public MultiServer() {
	}

	void runServer() throws IOException {
		server = new ServerSocket(MultiServer.PORT);

		Router router = Router.getInstance();
		router.add(new RegexpRoute(Method.GET, "^/$", new OKView()));
		router.add(new RegexpRoute(Method.POST,"^/user$", new UserRegisterView()));
		router.add(new RegexpRoute(Method.POST,"^/user/~promote$", new UserPromoteView()));
		router.add(new RegexpRoute(Method.GET, "^/user$", new UserListView()));
		router.add(new RegexpRoute(Method.GET, "^/~save$", new SaveJSONView()));
		// Question CRUD
		// C
		router.add(new RegexpRoute(Method.POST,   "^/question$", new QuestionCreateView()));
		// R
		router.add(new RegexpRoute(Method.GET,    "^/question$", new QuestionListView()));
		router.add(new RegexpRoute(Method.GET,    "^/question/([0-9]+)$", new QuestionGetView()));
		// U
		router.add(new RegexpRoute(Method.POST,   "^/question/([0-9]+)$", new QuestionUpdateView()));
		// D
		router.add(new RegexpRoute(Method.DELETE, "^/question/([0-9]+)$", new QuestionDeleteView()));
		
		// Survey
		router.add(new RegexpRoute(Method.GET, "^/survey$", new SurveyGetView()));
		router.add(new RegexpRoute(Method.POST, "^/survey$", new SurveyCheckView()));
		
		System.out.println("Server run ... ");

		while (true) {

			Socket socket = server.accept();
			System.out.println("New client");
			new ClientThread(socket).run();
		}

	}

	public static void main(String[] args) {
		UserStorage.getInstance().load("user.db");
		QuestionStorage.getInstance().load("question.db");
		try {
			new MultiServer().runServer();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}