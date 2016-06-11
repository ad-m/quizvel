package core.model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class Question extends DataModel implements Iterable<Choice> {
	private static final long serialVersionUID = 8291807221891888120L;
	String text;
	private ArrayList<Choice> choices;
	int correct_id;

	public Question() {
		this.choices = new ArrayList<Choice>();
	}

	public Question(String text) {
		this();
		this.text = text;
	}

	public Question(JSONObject obj) {
		this();
		this.text = obj.getString("text");
		this.correct_id = obj.getInt("correctId");
		JSONArray choices = obj.getJSONArray("choices");
		for (int i = 0; i < choices.length(); i++) {
			add(new Choice(choices.getJSONObject(i)));
		}
	}

	public String getText() {
		return text;
	}

	public Iterator<Choice> iterator() {
		return choices.iterator();
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean add(Choice arg0) {
		return choices.add(arg0);
	}

	public void clear() {
		choices.clear();
	}

	public int size() {
		return choices.size();
	}

	public <T> T[] toArray(T[] arg0) {
		return choices.toArray(arg0);
	}

	public int getCorrectId() {
		return correct_id;
	}

	public void setCorrectId(int correct) {
		if (correct <= 0 && correct >= this.choices.size()) {
			throw new IllegalStateException("This correct is inlegal.");
		}
		this.correct_id = correct;
	}

	public Choice getCorrect() {
		if (this.choices.size() != 0 && this.choices.size() > this.correct_id && this.correct_id != -1) {
			return this.choices.get(this.correct_id);
		}
		return null;
	}

	public boolean validate(int choice) {
		return choice == this.correct_id;
	}

	public boolean validate(Choice choice) {
		return this.getCorrect().equals(choice);
	}

	@Override
	public String toString() {
		return "Question [text=" + text + ", choices=" + choices + ", getCorrect()=" + getCorrect() + "]";
	}

	@Override
	public JSONObject toJSON() {
		JSONObject obj = new JSONObject(this);
		JSONArray choices = new JSONArray();
		for (Choice choice : this.choices) {
			choices.put(choice.toJSON());
		}
		obj.put("choices", choices);
		return obj;
	}

	public static void main(String[] args) {
		Question question = new Question("Why?");
		question.add(new Choice("YES"));
		question.add(new Choice("NO"));
		System.out.println(new Question(question.toJSON()).toJSON().toString().equals(question.toJSON().toString()));
		question.setCorrectId(2);
		assert question.getCorrect() == null;
		question.setCorrectId(1);
		assert question.getCorrect().toJSON().toString().equals(new Choice("NO").toJSON().toString());
	}

	@Override
	public Question clone() {
		return new Question(this.toJSON());
	}

}
