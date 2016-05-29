package view;

import http.Request;
import http.Response;
import model.User;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractAuthenticatedJSONView extends AbstractAuthenticatedView {
	
	public Response getResponse(Request request, User user) {
		try {
			return new Response(this.getJSON(request, user).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new Response("Error occurs", 500);
	}

	public abstract JSONObject getJSON(Request request, User user) throws JSONException;
}
