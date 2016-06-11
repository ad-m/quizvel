package server.storage;

import java.util.Random;

import core.model.Question;
import core.model.Survey;

/**
 * Singleton bazy danych pytań
 * 
 * @author adas
 *
 */
public class QuestionStorage extends AbstractStoreData<Question> {
	public final static QuestionStorage INSTANCE = new QuestionStorage();
	private static final Random rand = new Random();

	private QuestionStorage() {
	}

	/**
	 * @return instancja bazy danych
	 */
	public static QuestionStorage getInstance() {
		return INSTANCE;
	}

	public Survey getSurvey(int size) {
		int max = QuestionStorage.getInstance().size();
		Survey survey = new Survey();
		for (int i = 0; i < size; i++) {
			int id = rand.nextInt(max);
			survey.add(id, this.get(id));
		}
		return survey;
	}

	public Survey getSurvey() {
		return this.getSurvey(Survey.COUNT);
	}

	public Survey getSurvey(String numbers) {
		Survey survey = new Survey();
		for (String s : numbers.split(Survey.SEPERATOR)) {
			int id = Integer.parseInt(s);
			survey.add(id, this.get(id));
		}
		return survey;
	}
}
