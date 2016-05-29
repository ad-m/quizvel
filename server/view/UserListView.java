package view;

import http.Request;
import model.User;
import storage.UserStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserListView extends AbstractJSONView {

	public JSONObject getJSON(Request request) throws JSONException{

		UserStorage users = UserStorage.getInstance();
		
		JSONArray user_list = new JSONArray();
		  for (User user: users) {
			  user_list.put(user.toJSON());
		  };
		JSONObject resp = new JSONObject();
		resp.put("users", user_list);
		
		return resp;
	}

}
