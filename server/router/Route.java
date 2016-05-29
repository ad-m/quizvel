package router;

import http.Request;
import view.AbstractView;

interface Route {

	public abstract boolean match(Request request);

	public abstract AbstractView getView();

}