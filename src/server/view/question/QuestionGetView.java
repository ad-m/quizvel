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

public class QuestionGetView extends AbstractAdminJSONView {
	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+?)$");

	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		int id = QuestionGetView.getID(request.url);
		return QuestionStorage.getInstance().get(id).toJSON();
	}

	private static Integer getID(String url) {
		Matcher matcher = RE_PARSE_URL.matcher(url);
		matcher.find();
		return Integer.valueOf(matcher.group(1));
	}

	public static void main(String[] args) {
		Question question = new Question("XBC");
		QuestionStorage.getInstance().add(question);
		assert new QuestionGetView().getJSON(new Request("questions/0", "GET"), null).toString()
				.equals(question.toJSON().toString());
		assert QuestionGetView.getID("questions/123") == 123;
	}

}
