package server.view;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;

public class QuestionListView extends AbstractAdminJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage questions_db = QuestionStorage.getInstance();
		JSONArray question_list = new JSONArray();

		for (Question q : questions_db) {
			question_list.put(q.toJSON());
		}
		JSONObject resp = new JSONObject();
		resp.put("questions", question_list);
		return resp;
	}

}
