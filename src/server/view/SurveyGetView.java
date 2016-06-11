package server.view;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Session;
import core.model.Survey;
import core.model.User;
import server.storage.QuestionStorage;
import server.storage.SessionStorage;
import server.view.generic.AbstractSessionAuthenticatedJSONView;

public class SurveyGetView extends AbstractSessionAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user, Session session) throws JSONException {
		Survey survey = QuestionStorage.getInstance().getSurvey();
		SessionStorage.getInstance().get(user).put("question", survey.toString());
		JSONObject resp = new JSONObject();
		resp.put("questions", survey.toJSONArray());
		return resp;

	}
}
