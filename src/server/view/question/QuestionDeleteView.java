package server.view.question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.User;
import server.storage.QuestionStorage;
import server.view.generic.AbstractAdminJSONView;

public class QuestionDeleteView extends AbstractAdminJSONView {
	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+)$");

	private static Integer getId(String url) {
		Matcher matcher = RE_PARSE_URL.matcher(url);
		matcher.find();
		return Integer.valueOf(matcher.group(1));
	}

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		QuestionStorage.getInstance().remove(QuestionDeleteView.getId(request.url));
		JSONObject resp = new JSONObject();
		resp.put("status", "OK");
		return resp;
	}

}
