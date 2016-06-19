package server.storage;

import java.util.Arrays;
import java.util.function.Predicate;

import core.model.User;

public class UserStorage extends AbstractStoreData<User> {
	public final static UserStorage INSTANCE = new UserStorage();

	private UserStorage() {
	}

	/**
	 * dodawanie użytkownika do bazy danych z weryfikacja unikalnosci nazwy
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see server.storage.AbstractStoreData#add(java.lang.Object)
	 */
	@Override
	public boolean add(User arg0) {
		if (findByUserName(arg0.getUsername()) != null) {
			return false;
		}
		return super.add(arg0);
	}

	public static UserStorage getInstance() {
		return INSTANCE;
	}

	/**
	 * wyszukiwanie użytkowników po nazwie
	 * 
	 * @param username
	 *            nazwa użytkownika
	 * @return użytkownik odnaleziony
	 */
	public User findByUserName(final String username) {
		return this.first(new Predicate<User>() {

			@Override
			public boolean test(User t) {
				return t.getUsername().equals(username);
			}
		});
	}

	/**
	 * sprawdzenie poprawnosic danych autoryzacjnych
	 * 
	 * @param username
	 *            nazwa użytkownika
	 * @param password
	 *            hasło użytkownika
	 * @return użytkownik autoryzowany
	 */
	public User authenticate(String username, String password) {
		User user = findByUserName(username);
		return (user != null && user.checkPassword(password)) ? user : null;
	}

	public static void main(String[] args) {
		UserStorage store = new UserStorage();
		User u1 = new User("Alice", "pass");
		assert store.add(u1);
		assert u1.equals(store.findByUserName("Alice"));
		assert Arrays.equals(new User[] { u1 }, store.toArray());
		assert store.size() == 1;
	}
}
