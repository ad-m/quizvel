package storage;

import java.util.HashMap;
import java.util.Map;

import model.Session;
import model.User;

public class SessionStorage {
	public final static SessionStorage INSTANCE = new SessionStorage();
	private Map<User, Session> content = new HashMap<User, Session>();
	
	private SessionStorage() {
	}

	public static SessionStorage getInstance() {
		return INSTANCE;
	}

	public Session get(Object key) {
		return content.get(key) != null ? content.get(key) : new Session();
	}	
	
	
	
}
