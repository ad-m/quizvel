package client.window;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * Klasa odpowiedzialna za implementacje AbstractListModel na potrzeby
 * wyświetlenie listy obiektów w poszczególnych częściach aplikacji.
 * 
 * @author adas
 *
 * @param <T>
 *            obiekt przechowywany w modelu
 */
public class ListModel<T> extends AbstractListModel<T> implements Iterable<T> {
	private static final long serialVersionUID = 7094310649646444688L;
	protected List<T> data;

	public ListModel(List<T> list) {
		super();
		this.data = list;
	}

	public ListModel() {
		this(new ArrayList<T>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return this.data.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public T getElementAt(int index) {
		return this.data.get(index);
	}

	/**
	 * Metoda odpowiedzialna za dodanie obiektu do listy i aktualizacje widoku.
	 * 
	 * @param obj
	 *            dodawany obiekt
	 */
	public void addElement(T obj) {
		data.add(obj);
		fireIntervalAdded(this, data.size() - 1, data.size() - 1);
	}

	/**
	 * Metoda odpowiedzialna za usuwanie obiektu z listy i aktualizacje widoku.
	 * 
	 * @param index
	 */
	public void remove(int index) {
		data.remove(index);
		fireIntervalRemoved(this, index, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	public Iterator<T> iterator() {
		return data.iterator();
	}

	/**
	 * Metoda odpowiedzialna za dodanie listy obiektów do obecnej listy.
	 * 
	 * @param objs
	 *            lista obiektów do dodania.
	 */
	public void addAll(List<T> objs) {
		for (T obj : objs) {
			addElement(obj);
		}
	}

	/**
	 * Metoda odpowiedzialna za nadpisanie obiektów na liście.
	 * 
	 * @param index
	 * @param element
	 * @return
	 */
	public T set(int index, T element) {
		fireContentsChanged(this, index, index);
		return data.set(index, element);
	}

}