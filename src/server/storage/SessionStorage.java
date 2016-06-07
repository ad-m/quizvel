package server.storage;

import java.util.HashMap;
import java.util.Map;

import core.model.Session;
import core.model.User;

public class SessionStorage {
	public final static SessionStorage INSTANCE = new SessionStorage();
	private Map<User, Session> content = new HashMap<User, Session>();

	private SessionStorage() {
	}

	public static SessionStorage getInstance() {
		return INSTANCE;
	}

	public Session get(User key) {
		if (!content.containsKey(key)) {
			content.put(key, new Session());
		}
		return content.get(key);
	}

}
