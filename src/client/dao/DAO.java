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

/**
 * Klasa odpowiedzialna za implementacje konfiguracji z moim serwerem i
 * wykonanie wszystkich operacji na danych i transmisji informacji.
 * 
 * @author adas
 *
 */
public class DAO extends AbstractDAO {
	public final static DAO INSTANCE = new DAO();

	private DAOUser user;

	private DAO() {
	}

	private boolean isOK(Response resp) {
		return resp.getStatus() == 200 && resp.getJSON().getString("status").equalsIgnoreCase("OK");
	}

	public static DAO getInstance() {
		return INSTANCE;
	}

	/**
	 * sprawdzenie stanu serwera czy funkcjonuje prawidłowo i komunikacji z nim
	 * 
	 * @return poprawność stanu serwera
	 */
	public boolean checkHealt() {
		try {
			return this.send(new Request("/", "GET")).getStatus() == 200;
		} catch (IOException | ServerErrorException e) {
			return false;
		}
	}

	/**
	 * Metoda odpowiedzialna za zapisanie pamięci operacyjnej do pliku
	 * 
	 * @return poprawność odpowiedzi
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean save() throws IOException, ServerErrorException {
		Response response = this.send(new Request("/~save", "GET"), this.user);
		return isOK(response);
	}

	/**
	 * Metoda odpowiedzialna za probę zalogowania użytkownika i pobranie danych
	 * o użytkowniku zalogowanym.
	 * 
	 * @param username
	 *            login użytkownika logującego się
	 * @param password
	 *            hasło użytkownika logującego się
	 * @return użytkownik zalogowany
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public User authenticate(String username, String password) throws IOException, ServerErrorException {
		DAOUser new_user = new DAOUser(username, password);
		Response resp = this.send(new Request("/user/~current", "GET", ""), new_user);
		if (resp.getStatus() == 200) {
			this.user = new_user;
			return new User(resp.getJSON());
		}
		return null;
	}

	/**
	 * Metoda odpowiedzialna za rejestracje na serwerze użytkownika z podanymi
	 * danymi uwierzytelniającymi.
	 * 
	 * @param username
	 *            login nowego użytkownika
	 * @param password
	 *            hasło nowego użytkownika
	 * @return poprawność rejestracji
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean register(String username, String password) throws IOException, ServerErrorException {
		JSONObject body = new JSONObject();
		body.put("username", username);
		body.put("password", password);
		Response resp = this.send(new Request("/user", "POST", body));
		if (resp.getJSON().getString("status").equals("OK")) {
			this.user = new DAOUser(username, password);
			return true;
		}
		return false;
	}

	/**
	 * Metoda odpowiedzialna za komunikacje promocji użytkownika na konto
	 * administratora
	 * 
	 * @param secret
	 *            poufne hasło promocyjne
	 * @return poprawność promocji
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean promote(String secret) throws UnknownHostException, IOException, ServerErrorException {
		JSONObject body = new JSONObject();
		body.put("secret", secret);
		Response resp = this.send(new Request("/user/~promote", "POST", body), this.user);
		return resp.getStatus() == 200 && resp.getJSON().getString("status").equals("OK");
	}

	/**
	 * Metoda odpowiedzialna za odczytanie listy użytkowników z serwera.
	 * 
	 * @return lista użytkowników w bazie operacyjnej
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public List<User> getUsers() throws IOException, ServerErrorException {
		List<User> users = new LinkedList<User>();
		Response resp = this.send(new Request("/user", "GET"));
		JSONArray json_users = resp.getJSON().getJSONArray("users");
		for (int i = 0; i < json_users.length(); i++) {
			users.add(new User(json_users.getJSONObject(i)));
		}
		return users;
	}

	/**
	 * Metoda odpowiedzialna za przesłanie nowego pytania
	 * 
	 * @param question
	 *            pytanie do zapisania
	 * @return poprawność zapisania pytania
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean saveQuestion(Question question) throws IOException, ServerErrorException {
		Response resp = this.send(new Request("/question", "POST", question.toJSON()), user);
		return resp.getStatus() == 200 && resp.getJSON().getString("status").equals("OK");
	}

	/**
	 * Metoda odpowiedzialna za odczytanie pełnej listy pytań w pamięci
	 * operacyjnej.
	 * 
	 * @return lista pytań w pamięci operacyjnej
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public List<Question> getQuestions() throws IOException, ServerErrorException {
		return getQuestionList("/question");
	}

	private List<Question> getQuestionList(String uri) throws IOException, ServerErrorException {
		List<Question> objects = new LinkedList<Question>();
		Response resp = this.send(new Request(uri, "GET"), this.user);
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

	/**
	 * Metoda odpowiedzialna za odczytanie wybranego pytania
	 * 
	 * @param id
	 *            identyfikator odczytaniego pytania
	 * @return pytanie odczytane o podanym ID
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public Question getQuestion(int id) throws IOException, ServerErrorException {
		Response resp = this.send(new Request("/question/" + id, "GET"), user);
		if (resp.getStatus() != 200) {
			return null;
		}
		return new Question(resp.getJSON());
	}

	/**
	 * Metoda odpowiedzialna za nadpisanie pytania o wybranym identyfikatorze
	 * nowym pytaniem
	 * 
	 * @param id
	 *            identyfikator odczytanego pytania
	 * @param question
	 *            pytanie nadpisujące
	 * @return poprawność nadpisania pytania
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean saveQuestion(int id, Question question) throws IOException, ServerErrorException {
		return isOK(this.send(new Request("/question/" + id, "POST", question.toJSON()), user));
	}

	/**
	 * Metoda odpowiedzialna za usunięcie pytania o wybranym identyfiaktorze
	 * 
	 * @param id
	 *            identyfikator odczytanego pytania
	 * @return
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public boolean deleteQuestion(int id) throws IOException, ServerErrorException {
		return isOK(this.send(new Request("/question/" + id, "DELETE"), user));
	}

	/**
	 * Metoda odpowiedzialna za odczytanie ankiety do odgadniecia przez
	 * użytkownika
	 * 
	 * @return lista pytań do odgadnięcia
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public List<Question> getSurvey() throws IOException, ServerErrorException {
		return getQuestionList("/survey");
	}

	/**
	 * Metoda odpowiedzialna za sprawdzenie wyników zadanej ankiety.
	 * 
	 * @param list
	 *            lista z numerami odpowiedziami na pytania
	 * @return liczba osiągniętych punktów
	 * @throws IOException
	 * @throws ServerErrorException
	 */
	public int checkSurvey(List<Integer> list) throws IOException, ServerErrorException {
		JSONArray answers = new JSONArray();
		for (Integer answer : list) {
			answers.put(answer);
		}
		JSONObject json = new JSONObject();
		json.put("answers", answers);
		Response resp = this.send(new Request("/survey", "POST", json), user);
		if (resp.getStatus() != 200) {
			return 0;
		}
		return resp.getJSON().getInt("point");

	}

	/**
	 * Wykoanie testowej komunikacji z serwerem
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DAO dao = DAO.getInstance();
		try {
			System.out.println(dao.checkHealt());
			System.out.println(dao.authenticate("xyz", "xyz"));

			System.out.println(dao.getQuestions().size());
			for (int i = 0; i <= 25; i++) {
				Question question = new Question("Updated question" + i);
				question.add(new Choice("A"));
				question.add(new Choice("B"));
				question.add(new Choice("C"));
				question.setCorrectId(0);
				dao.saveQuestion(question);
			}
			// System.out.println(dao.save());
			System.out.print(dao.getQuestions().size());
			dao.save();
		} catch (IOException | ServerErrorException e) {
			e.printStackTrace();
		}
	}

};
