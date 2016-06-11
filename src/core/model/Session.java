package core.model;

import java.util.HashMap;
import java.util.Map;

public class Session {
	Map<String, String> content;

	public Session() {
		this.content = new HashMap<String, String>();
	}

	public boolean isEmpty() {
		return content.isEmpty();
	}

	public String get(String key) {
		return content.get(key);
	}

	public String put(String key, String value) {
		return content.put(key, value);
	}

	public String remove(String key) {
		return content.remove(key);
	}

	public String replace(String key, String value) {
		return content.replace(key, value);
	}

	public void clear() {
		content.clear();
	}

}
