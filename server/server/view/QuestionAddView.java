package server.view;

import server.storage.QuestionStorage;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;

public class QuestionAddView extends AbstractAdminJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage.getInstance().add(new Question().fromJSON(request.getJSON()));
		JSONObject resp = new JSONObject();
		resp.put("status", "ok");
		return resp;
	}

}
