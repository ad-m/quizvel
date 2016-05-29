package model;

import org.json.JSONObject;

public abstract class DataModel {
	public abstract JSONObject toJSON();
	public abstract DataModel fromJSON(JSONObject obj);
}
