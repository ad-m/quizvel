package server.view;

import server.storage.QuestionStorage;
import server.storage.UserStorage;
import server.view.generic.AbstractAuthenticatedJSONView;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;

public class SaveJSONView extends AbstractAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		JSONObject resp = new JSONObject();
		if(user.isAdmin()){
			UserStorage.getInstance().save("user.db");
			QuestionStorage.getInstance().save("question.db");
			resp.put("status", "OK");
		} else {
			resp.put("status", "Admin-only");
		}
		return resp;
	}

}
