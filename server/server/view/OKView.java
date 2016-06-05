package server.view;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;

public class OKView extends AbstractJSONView {

	public JSONObject getJSON(Request request) {
		JSONObject json = new JSONObject();
		try {
			json.put("status", "OK");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

}
