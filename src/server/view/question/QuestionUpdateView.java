package server.view.question;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;

public class QuestionUpdateView extends AbstractQuestionIDView {
	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		JSONObject req = request.getJSON();

		QuestionStorage.getInstance().set(QuestionUpdateView.getID(request.getUrl()), new Question(req));
		JSONObject resp = new JSONObject();
		resp.put("status", "OK");
		return resp;
	}

}
