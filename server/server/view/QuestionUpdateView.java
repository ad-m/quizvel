package server.view;

import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;

public class QuestionUpdateView extends AbstractAdminJSONView {

	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+)$");

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		int id = Integer.valueOf(RE_PARSE_URL.matcher(request.url).group(1));
		JSONObject req = request.getJSON();

		QuestionStorage.getInstance().set(id, new Question(req));
		JSONObject resp = new JSONObject();
		resp.put("status", "ok");
		return resp;
	}

}
