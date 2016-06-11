package core.model;

import org.json.JSONObject;

public class Choice extends DataModel {
	private static final long serialVersionUID = 6022085637930721558L;
	private String text;

	public Choice(String text) {
		this.text = text;
	}

	public Choice(JSONObject obj) {
		this(obj.getString("text"));
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Choice [text=" + text + "]";
	}

	public JSONObject toJSON() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("text", this.text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public boolean equals(Choice obj) {
		return this.text.equals(obj.text);
	}

	public static void main(String[] args) {
		Choice obj = new Choice("Opcja 1");
		JSONObject json = obj.toJSON();
		assert json.toString().equals("{\"text\":\"Opcja 1\"}");
		assert new Choice(json).equals(obj);
	}

	public Choice clone() throws CloneNotSupportedException {
		return new Choice(this.toJSON());
	}
}
