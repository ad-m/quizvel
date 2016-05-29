package view;

import http.Request;
import model.Question;
import model.User;
import storage.QuestionStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionAddView extends AbstractAdminJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage.getInstance().add(new Question().fromJSON(request.getJSON()));
		JSONObject resp = new JSONObject();
		resp.put("status", "ok");
		return resp;
	}

}
