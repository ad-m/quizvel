package server.router;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import core.http.Request;
import server.view.generic.AbstractView;

/**
 * Klasa sterujaca przetwarzaniem żądania i kierująca do odpowiedniego widoku
 * zwracajego dane
 * 
 * @author adas
 *
 */
public class Router {
	private static final Router INSTANCE = new Router();
	public List<Route> list = new LinkedList<Route>();

	public static Router getInstance() {
		return INSTANCE;
	}

	public int size() {
		return list.size();
	}

	public boolean add(Route e) {
		return list.add(e);
	}

	public boolean addAll(Collection<? extends Route> c) {
		return list.addAll(c);
	}

	public AbstractView find(Request request) {
		for (Route r : list) {
			if (r.match(request)) {
				return r.getView();
			}
		}
		return null;
	}
}
