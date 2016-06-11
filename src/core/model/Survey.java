package core.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

import server.core.Config;

public class Survey implements Iterable<Question> {
	public static final int COUNT = Config.QUESTION_COUNT;
	public static final String SEPERATOR = ";";

	private List<Question> questions;
	private List<Integer> ids;

	public Survey() {
		ids = new ArrayList<Integer>();
		questions = new ArrayList<Question>();

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ids.size() - 1; i++) {
			sb.append(ids.get(i)).append(Survey.SEPERATOR);
		}
		sb.append(ids.get(ids.size() - 1));
		return sb.toString();
	}

	public Iterator<Question> iterator() {
		return questions.iterator();
	}

	public Question get(int index) {
		return questions.get(index);
	}

	public JSONArray toJSONArray() {
		JSONArray question_list = new JSONArray();

		for (Question question : this) {
			question_list.put(question.toJSON());
		}
		return question_list;
	}

	public void add(Integer id, Question question) {
		this.ids.add(id);
		this.questions.add(question);
	}

	public int size() {
		return ids.size();
	}

}
