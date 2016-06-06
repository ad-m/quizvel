package server.view;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;

public class CurrentUserView extends AbstractAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		return user.toJSON();
	}

}
