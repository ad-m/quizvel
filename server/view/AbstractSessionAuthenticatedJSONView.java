package view;

import http.Request;
import model.Session;
import model.User;
import storage.SessionStorage;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractSessionAuthenticatedJSONView extends
		AbstractAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		Session session = SessionStorage.getInstance().get(user);
		return getJSON(request, user, session);
	}

	abstract public JSONObject getJSON(Request request, User user, Session session) throws JSONException;

}
