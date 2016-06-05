package server.view;

import java.util.regex.Pattern;

import server.storage.QuestionStorage;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;

public class QuestionGetView extends AbstractAdminJSONView {
	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+)$");
	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		int id = Integer.valueOf(RE_PARSE_URL.matcher(request.url).group(1));
		return QuestionStorage.getInstance().get(id).toJSON();
	}

}
