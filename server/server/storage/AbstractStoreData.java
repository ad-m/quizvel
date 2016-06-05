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
import java.util.ListIterator;
import java.util.function.Predicate;

public abstract class AbstractStoreData<T> implements Iterable<T> {
	public LinkedList<T> content;

	protected AbstractStoreData() {
		this.content = new LinkedList<T>();
	}

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

	public void save(String filename) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			for (T o : this.content) {
				oos.writeObject(o);
			}
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void add(int arg0, T arg1) {
		content.add(arg0, arg1);
	}

	public boolean add(T arg0) {
		return content.add(arg0);
	}

	public boolean contains(Object arg0) {
		return content.contains(arg0);
	}

	public int size() {
		return content.size();
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		T[] array = (T[]) content.toArray();
		return array;
	}

	public LinkedList<T> filter(Predicate<T> p) {
		LinkedList<T> result = new LinkedList<T>();
		for (T obj : this.content) {
			if (p.test(obj)) {
				result.add(obj);
			}
		}
		return result;
	}

	public T first(Predicate<T> p) {
		for (T obj : this.content) {
			if (p.test(obj)) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "AbstractStoreData [content=" + content + "]";
	}

	public Iterator<T> iterator() {
		return content.iterator();
	}

	public ListIterator<T> listIterator() {
		return content.listIterator();
	}

	public T get(int index) {
		return content.get(index);
	}

	public T set(int index, T element) {
		return content.set(index, element);
	}

	public T remove(int index) {
		return content.remove(index);
	}

}