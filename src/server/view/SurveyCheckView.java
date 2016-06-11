package server.view;

import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Session;
import core.model.Survey;
import core.model.User;
import server.core.Config;
import server.storage.QuestionStorage;
import server.storage.SessionStorage;
import server.view.generic.AbstractSessionAuthenticatedJSONView;

public class SurveyCheckView extends AbstractSessionAuthenticatedJSONView {
	public static final int COUNT = Config.QUESTION_COUNT;

	public static final Random rand = new Random();

	@Override
	public JSONObject getJSON(Request request, User user, Session session) throws JSONException {
		String question_str = SessionStorage.getInstance().get(user).get("question");
		Survey survey = QuestionStorage.getInstance().getSurvey(question_str);
		int point = 0;

		int i = 0;
		JSONArray answers = request.getJSON().getJSONArray("answers");
		while (survey.size() < i && answers.length() < i) {
			if (survey.get(i).validate(answers.getInt(i))) {
				point += 1;
			}
			i++;
		}

		JSONObject resp = new JSONObject();
		resp.put("point", point);
		return resp;

	}

}