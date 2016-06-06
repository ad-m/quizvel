package client.dao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import core.http.Request;
import core.http.Response;
import core.model.Choice;
import core.model.Question;
import core.model.User;

public class DAO extends AbstractDAO {
	public final static DAO INSTANCE = new DAO();

	private DAOUser user;

	private DAO() {
	}

	public static DAO getInstance() {
		return INSTANCE;
	}

	public boolean healt_check() {
		try {
			return this.send_request(new Request("/", "GET")).getStatus() == 200;
		} catch (IOException | ServerErrorException e) {
			return false;
		}
	}

	public boolean save() throws IOException, ServerErrorException {
		Response response = this.send_request(new Request("/~save", "GET"), this.user);
		return is_ok_response(response);
	}

	public User authenticate(String username, String password) throws IOException, ServerErrorException {
		DAOUser new_user = new DAOUser(username, password);
		Response resp = this.send_request(new Request("/user/~current", "GET", ""), new_user);
		if (resp.getStatus() == 200) {
			this.user = new_user;
			return new User(resp.getJSON());
		}
		return null;
	}

	public boolean register(String username, String password) throws IOException, ServerErrorException {
		JSONObject body = new JSONObject();
		body.put("username", username);
		body.put("password", password);
		Response resp = this.send_request(new Request("/user", "POST", body));
		if (resp.getJSON().getString("status").equals("OK")) {
			this.user = new DAOUser(username, password);
			return true;
		}
		return false;
	}

	public boolean promote(String secret) throws UnknownHostException, IOException, ServerErrorException {
		JSONObject body = new JSONObject();
		body.put("secret", secret);
		Response resp = this.send_request(new Request("/user/~promote", "POST", body), this.user);
		return resp.getStatus() == 200 && resp.getJSON().getString("status").equals("OK");
	}

	public List<User> getUsers() throws IOException, ServerErrorException {
		List<User> users = new LinkedList<User>();
		Response resp = this.send_request(new Request("/user", "GET"));
		JSONArray json_users = resp.getJSON().getJSONArray("users");
		for (int i = 0; i < json_users.length(); i++) {
			users.add(new User(json_users.getJSONObject(i)));
		}
		return users;
	}

	public boolean saveQuestion(Question question) throws IOException, ServerErrorException {
		Response resp = this.send_request(new Request("/question", "POST", question.toJSON()), user);
		return resp.getStatus() == 200 && resp.getJSON().getString("status").equals("OK");
	}

	public List<Question> getQuestions() throws IOException, ServerErrorException {
		return get_question_list("/question");
	}

	private List<Question> get_question_list(String uri) throws IOException, ServerErrorException {
		List<Question> objects = new LinkedList<Question>();
		Response resp = this.send_request(new Request(uri, "GET"), this.user);
		JSONObject json = resp.getJSON();
		if (json.has("status") && json.getString("status").equals("Admin-only")) {
			return null;
		}
		JSONArray json_objects = json.getJSONArray("questions");
		for (int i = 0; i < json_objects.length(); i++) {
			objects.add(new Question(json_objects.getJSONObject(i)));
		}
		return objects;
	}

	public Question getQuestion(int id) throws IOException, ServerErrorException {
		Response resp = this.send_request(new Request("/question/" + id, "GET"), user);
		if (resp.getStatus() != 200) {
			return null;
		}
		return new Question(resp.getJSON());
	}

	public boolean saveQuestion(int id, Question question) throws IOException, ServerErrorException {
		Response resp = this.send_request(new Request("/question/" + id, "POST", question.toJSON()), user);
		return is_ok_response(resp);
	}

	private boolean is_ok_response(Response resp) {
		return resp.getStatus() == 200 && resp.getJSON().getString("status").toLowerCase().equals("OK");
	}

	public boolean deleteQuestion(int id) throws IOException, ServerErrorException {
		Response resp = this.send_request(new Request("/question/" + id, "DELETE"), user);
		return is_ok_response(resp);
	}

	public List<Question> getSurvey() throws IOException, ServerErrorException {
		return get_question_list("/survey");
	}

	public int checkSurvey(List<Integer> list) throws IOException, ServerErrorException {
		JSONArray answers = new JSONArray();
		for (Integer answer : list) {
			answers.put(answer);
		}
		JSONObject json = new JSONObject();
		json.put("answers", answers);
		Response resp = this.send_request(new Request("/survey", "POST", json), user);
		if (resp.getStatus() != 200) {
			return 0;
		}
		return resp.getJSON().getInt("point");

	}

	public static void main(String[] args) {
		DAO dao = DAO.getInstance();
		try {
			System.out.println(dao.healt_check());
			System.out.println(dao.getUsers());
			System.out.println(dao.authenticate("xyz", "xyz"));
			System.out.println(dao.getUsers());
			System.out.println(dao.getQuestions());
			System.out.println(dao.saveQuestion(new Question("New question")));
			System.out.println(dao.getQuestion(0));
			System.out.println(dao.getQuestions());
			Question question = new Question("Updated question");
			question.add(new Choice("A"));
			question.add(new Choice("B"));
			question.add(new Choice("C"));
			System.out.println(dao.saveQuestion(0, question));
			System.out.println(dao.getQuestions());
			System.out.println(dao.getQuestion(0));
			System.out.println(dao.getSurvey());
		} catch (IOException | ServerErrorException e) {
			e.printStackTrace();
		}
	}

};
