package server.router;

import core.http.Request;
import server.view.generic.AbstractView;

/**
 * interfejs reguły przetwarzania żądania
 * 
 * @author adas
 *
 */
interface Route {

	/**
	 * @param request
	 * @return weryfikacja poprawności żadania dla danej reguły przetwarzania.
	 */
	public abstract boolean match(Request request);

	/**
	 * @return zwrócenie klasy widoku reguły
	 */
	public abstract AbstractView getView();

}