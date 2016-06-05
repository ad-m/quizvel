package server.view;

import core.http.HTTPObject;
import core.http.Request;

public abstract class AbstractView {
	public abstract HTTPObject getResponse(Request request);
}
