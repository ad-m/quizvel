package server.storage;

import core.model.Question;

/**
 * Singleton bazy danych pytań
 * 
 * @author adas
 *
 */
public class QuestionStorage extends AbstractStoreData<Question> {
	public final static QuestionStorage INSTANCE = new QuestionStorage();

	private QuestionStorage() {
	}

	/**
	 * @return instancja bazy danych
	 */
	public static QuestionStorage getInstance() {
		return INSTANCE;
	}

}
