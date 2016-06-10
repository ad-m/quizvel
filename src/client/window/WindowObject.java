package client.window;

/**
 * okno manipulacji obiektem danych
 * 
 * @author adas
 *
 * @param <T>
 *            obiekt danych
 */
public interface WindowObject<T> {

	/**
	 * @return stworzony w oknie obiekt
	 */
	public T getObject();

	/**
	 * aktualizacja danych z formularza
	 */
	public void updateObject();

	/**
	 * potwierdzenie aktualizacji
	 * 
	 * @param s
	 *            status pola
	 */
	public void setStatus(boolean s);

	/**
	 * @return pobranie statusu formularza
	 */
	public boolean getStatus();

	/**
	 * zamykanie okna
	 */
	public void dispose();

}