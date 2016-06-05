package server.view;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;

public class UserPromoteView extends AbstractAuthenticatedJSONView {

	public JSONObject getJSON(Request request, User user) throws JSONException{
		String pass = request.getJSON().getString("secret");
		JSONObject response = new JSONObject();

		if(pass !=null && pass.equals("Hero")){
			user.setAdmin(!user.isAdmin());
			response.put("status", "OK");
		} else {
			response.put("status", "FAIL PROMOTE");
		}
		
		return response;
	}
}
