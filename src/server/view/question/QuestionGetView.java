package server.view.question;

import org.json.JSONException;
import org.json.JSONObject;

import core.http.Request;
import core.model.Question;
import core.model.User;
import server.storage.QuestionStorage;

public class QuestionGetView extends AbstractQuestionIDView {
	@Override
	public JSONObject getJSON(Request request, User user) throws JSONException {
		int id = QuestionGetView.getID(request.getUrl());
		return QuestionStorage.getInstance().get(id).toJSON();
	}

	public static void main(String[] args) {
		Question question = new Question("XBC");
		QuestionStorage.getInstance().add(question);
		assert new QuestionGetView().getJSON(new Request("questions/0", "GET"), null).toString()
				.equals(question.toJSON().toString());
		assert QuestionGetView.getID("questions/123") == 123;
	}

}
