package storage;

import model.Question;

public class QuestionStorage extends AbstractStoreData<Question> {
	public final static QuestionStorage INSTANCE = new QuestionStorage();
	private QuestionStorage() {
	}
	public static QuestionStorage getInstance() {
		return INSTANCE;
	}
	

}
