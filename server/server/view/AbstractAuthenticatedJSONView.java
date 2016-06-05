package server.view;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.HTTPObject;
import core.http.Request;
import core.http.Response;
import core.model.User;

public abstract class AbstractAuthenticatedJSONView extends AbstractAuthenticatedView {
	
	public HTTPObject getResponse(Request request, User user) {
		try {
			return new Response(this.getJSON(request, user).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new Response("Error occurs", 500);
	}

	public abstract JSONObject getJSON(Request request, User user) throws JSONException;
}
