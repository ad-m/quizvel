package view;

import http.Request;
import http.Response;

public abstract class AbstractView {
	public abstract Response getResponse(Request request);
}
