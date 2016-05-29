package view;

import http.Request;

import model.User;
import storage.UserStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegisterView extends AbstractJSONView {

	public JSONObject getJSON(Request request) throws JSONException{
		JSONObject request_json = request.getJSON();
		String username = request_json.getString("username");
		String password = request_json.getString("password");
		
		UserStorage users = UserStorage.getInstance();
		JSONObject json = new JSONObject();
		json.put("status", users.add(new User(username, password)) ? "OK" : "FAIL");
		json.put("id", users.size());
		return json;
	}

}
