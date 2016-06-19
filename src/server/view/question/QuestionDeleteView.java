package server.view.question;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;
import server.storage.QuestionStorage;

public class QuestionDeleteView extends AbstractQuestionIDView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage.getInstance().remove(QuestionDeleteView.getID(request.getUrl()));
		JSONObject resp = new JSONObject();
		resp.put("status", "OK");
		return resp;
	}

}
