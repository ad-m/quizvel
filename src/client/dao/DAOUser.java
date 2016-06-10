package client.dao;

/**
 * Klasa odpowiedzialna za stworzenie obiektu - kontenera danych użytkownika
 * wykorzystywanego w wymagających logowania żądaniach do serwera.
 * 
 * @author adas
 *
 */
public class DAOUser {
	private String username;
	private String password;

	public DAOUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}