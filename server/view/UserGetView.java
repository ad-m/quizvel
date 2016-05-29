package view;

import http.Request;

import model.User;
import storage.UserStorage;

import org.json.JSONException;
import org.json.JSONObject;

public class UserGetView extends AbstractJSONView {

	public JSONObject getJSON(Request request) throws JSONException{
		JSONObject request_json = request.getJSON();
		String username = request_json.getString("username");
		String password = request_json.getString("password");
		
		UserStorage users = UserStorage.getInstance();
		
		JSONObject json = new JSONObject();
		User user = users.findByUserName(username);

		if(user == null){
			json.put("status", "Non-exists");
			return json;
		}

		if(user.checkPassword(password)){
			json.put("status", "OK");
		} else {
			json.put("status", "Wrong-password");
		}
		return json;
	}

}
