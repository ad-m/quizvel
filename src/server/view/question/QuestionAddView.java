package server.view.question;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;
import server.view.generic.AbstractAdminJSONView;

public class QuestionAddView extends AbstractAdminJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage.getInstance().add(new Question(request.getJSON()));
		JSONObject resp = new JSONObject();
		resp.put("status", "OK");
		return resp;
	}

}
