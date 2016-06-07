package server.view.user;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;
import server.view.generic.AbstractAuthenticatedJSONView;

public class UserCurrentView extends AbstractAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		return user.toJSON();
	}

}
