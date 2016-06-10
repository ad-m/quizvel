package server.storage;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Klasa odpowiedzialna za abstrakcyjny zbiornik danych (bazę danych plikową).
 * 
 * @author adas
 *
 * @param <T>
 */
public abstract class AbstractStoreData<T> implements Iterable<T> {
	public LinkedList<T> content;

	protected AbstractStoreData() {
		this.content = new LinkedList<T>();
	}

	/**
	 * wczytanie bazy z pliku
	 * 
	 * @param filename
	 *            dane pliku
	 */
	@SuppressWarnings("unchecked")
	public void load(String filename) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			T result;
			try {
				while ((result = (T) ois.readObject()) != null) {
					content.add(result);
				}
			} catch (EOFException e) {
				// We stop by exception, so this is ok to pass silence
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			ois.close();
		} catch (FileNotFoundException e1) {
			try {
				new FileOutputStream(new File(filename)).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 * metoda odpowiedzialna za zapisanie bazy danych do wskazanego pliku.
	 * 
	 * @param filename
	 *            zapisanie bazy danych do pliku
	 */
	public void save(String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			for (T o : this.content) {
				oos.writeObject(o);
			}
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param arg0
	 *            pozycja rekordu
	 * @param arg1
	 *            dodanie rekordu do bazy
	 */
	public void add(int arg0, T arg1) {
		content.add(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @return poprawność dodania rekordu
	 */
	public boolean add(T arg0) {
		return content.add(arg0);
	}

	/**
	 * czy obiekt jest zapisany w bazie danych
	 * 
	 * @param arg0
	 * @return wynik testu
	 */
	public boolean contains(Object arg0) {
		return content.contains(arg0);
	}

	/**
	 * sprawdzenie rozmiaru bazy danych
	 * 
	 * @return rozmiar bazy danych
	 */
	public int size() {
		return content.size();
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] array = (T[]) content.toArray();
		return array;
	}

	/**
	 * wyszukiwanie w bazie danych
	 * 
	 * @param p
	 *            reguła wyszukiwania w bazie
	 * @return obiektu odnalezione
	 */
	public LinkedList<T> filter(Predicate<T> p) {
		LinkedList<T> result = new LinkedList<T>();
		for (T obj : this.content) {
			if (p.test(obj)) {
				result.add(obj);
			}
		}
		return result;
	}

	/**
	 * wyszukiwanie jednego rekordu w bazie danych
	 * 
	 * @param p
	 *            reguła wyszukiwania w bazie danych
	 * @return pierwszy poprawny obiekt w bazie danych
	 */
	public T first(Predicate<T> p) {
		for (T obj : this.content) {
			if (p.test(obj)) {
				return obj;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractStoreData [content=" + content + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return content.iterator();
	}

	/**
	 * wyszukiwanie w bazie danych na podstawie ID
	 * 
	 * @param index
	 *            identyfikator rekordu
	 * @return obiekt bazy danych
	 */
	public T get(int index) {
		return content.get(index);
	}

	/**
	 * zastapienie rekordu w bazie danych
	 * 
	 * @param index
	 *            numer id w bazie danych
	 * @param element
	 *            element do zapisania
	 * @return
	 */
	public T set(int index, T element) {
		return content.set(index, element);
	}

	/**
	 * usuwanie i pobranie rekordu z bazy danych
	 * 
	 * @param index
	 *            numer id w bazie danych
	 * @return usuniety rekord
	 */
	public T remove(int index) {
		return content.remove(index);
	}

	/**
	 * @return cała baza danych w postaci listy
	 */
	public List<T> asList() {
		return content;
	}

}