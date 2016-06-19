package server.view.question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;
import server.view.generic.AbstractAdminJSONView;

public class QuestionUpdateView extends AbstractAdminJSONView {

	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+)$");

	private static Integer getID(String url) {
		Matcher matcher = RE_PARSE_URL.matcher(url);
		matcher.find();
		return Integer.valueOf(matcher.group(1));
	}

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		JSONObject req = request.getJSON();

		QuestionStorage.getInstance().set(QuestionUpdateView.getID(request.url), new Question(req));
		JSONObject resp = new JSONObject();
		resp.put("status", "OK");
		return resp;
	}

}
