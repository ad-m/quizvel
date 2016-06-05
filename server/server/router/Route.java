package server.router;

import core.http.Request;
import server.view.AbstractView;

interface Route {

	public abstract boolean match(Request request);

	public abstract AbstractView getView();

}