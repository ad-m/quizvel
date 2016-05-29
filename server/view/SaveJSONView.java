package view;

import http.Request;
import model.User;
import storage.QuestionStorage;
import storage.UserStorage;

import org.json.JSONException;
import org.json.JSONObject;

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
