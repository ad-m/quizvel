package server.view;

import core.http.Request;
import core.http.Response;

public abstract class AbstractView {
	public abstract Response getResponse(Request request);
}
