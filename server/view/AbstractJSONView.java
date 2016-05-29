package view;

import http.Request;
import http.Response;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractJSONView extends AbstractView {
	
	public Response getResponse(Request request) {
		try {
			return new Response(this.getJSON(request).toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new Response("Error occurs", 500);
	}

	public abstract JSONObject getJSON(Request request) throws JSONException;
}
