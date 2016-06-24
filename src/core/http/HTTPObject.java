package core.http;

import org.json.JSONObject;

/**
 * intefejs obiektu HTTP
 * 
 * @author adas
 *
 */
public interface HTTPObject {

	/**
	 * @return ciało żądania / odpowiedzi
	 */
	String getBody();

	/**
	 * @return przystepna forma tekstowa żądania
	 */
	String toString();

	/**
	 * @return żądanie w formie binarnej na potrzeby transmisji
	 */
	byte[] getBytes();

	/**
	 * @return JSON zapisany w ciale żądania
	 */
	JSONObject getJSON();
}