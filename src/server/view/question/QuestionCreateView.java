package server.view.question;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;
import server.view.generic.AbstractAdminJSONView;

public class QuestionCreateView extends AbstractAdminJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage qs = QuestionStorage.getInstance();
		qs.add(new Question(request.getJSON()));

		JSONObject response = new JSONObject();
		response.put("status", "OK");
		response.put("id", qs.size());
		return response;
	}

}
