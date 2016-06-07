package server.view;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Session;
import core.model.User;
import server.core.Config;
import server.storage.QuestionStorage;
import server.storage.SessionStorage;
import server.view.generic.AbstractSessionAuthenticatedJSONView;

public class SurveyGetView extends AbstractSessionAuthenticatedJSONView {
	public static final int COUNT = Config.QUESTION_COUNT;
	public static final String SEPERATOR = ";";
	public static final Random rand = new Random();

	private static int[] get_random() {
		int max = QuestionStorage.getInstance().size();
		int[] ints = new int[SurveyGetView.COUNT];
		for (int i = 0; i < SurveyGetView.COUNT; i++) {
			ints[i] = rand.nextInt(max);
		}
		return ints;
	}

	private static String array_to_string(int[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(array[i]).append(SurveyGetView.SEPERATOR);
		}
		sb.append(array[array.length - 1]);
		return sb.toString();
	}

	@Override
	public JSONObject getJSON(Request request, User user, Session session) throws JSONException {
		int[] question_ids = SurveyGetView.get_random();
		SessionStorage.getInstance().get(user).put("question", SurveyGetView.array_to_string(question_ids));
		JSONArray question_list = new JSONArray();

		for (int i = 0; i < question_ids.length; i++) {
			question_list.put(QuestionStorage.getInstance().get(question_ids[i]).toJSON());
		}
		JSONObject resp = new JSONObject();
		resp.put("questions", question_list);
		return resp;

	}

	public static void main(String[] args) {
		QuestionStorage.getInstance().load("question.db");
		System.out.print(SurveyGetView.array_to_string(SurveyGetView.get_random()));
	}

}
