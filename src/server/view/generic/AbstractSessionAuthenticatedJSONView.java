package server.view.generic;

import server.storage.SessionStorage;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Session;
import core.model.User;

public abstract class AbstractSessionAuthenticatedJSONView extends AbstractAuthenticatedJSONView {

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		Session session = SessionStorage.getInstance().get(user);
		return getJSON(request, user, session);
	}

	abstract public JSONObject getJSON(Request request, User user, Session session) throws JSONException;

}
