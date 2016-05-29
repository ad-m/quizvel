package view;

import java.util.Random;

import http.Request;
import model.Session;
import model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.Config;
import storage.QuestionStorage;
import storage.SessionStorage;

public class SurveyGetView extends AbstractSessionAuthenticatedJSONView {
	public static final int COUNT = Config.QUESTION_COUNT;
	public static final String SEPERATOR = ";";
	public static final Random rand =  new Random();
	private static int[] get_random(){
		int max = QuestionStorage.getInstance().size();
		int[] ints = new int[SurveyGetView.COUNT];
		for (int i = 1; i< SurveyGetView.COUNT; i++){
	      ints[i] = rand.nextInt(max);
	    }
		return ints;
	}
	private static String array_to_string(int[] array){
		
		StringBuilder sb = new StringBuilder(array[0]);
		for (int i=0; i<array.length; i++){
		    sb.append(SurveyGetView.SEPERATOR).append(array[i]);
		}
		return sb.toString();
	}
	@Override
	public JSONObject getJSON(Request request, User user, Session session) throws JSONException {
		int[] question_ids = SurveyGetView.get_random();
		SessionStorage.getInstance().get(user).put("question", SurveyGetView.array_to_string(question_ids));
		JSONArray question_list = new JSONArray();
		
		for(int i=0; i<question_ids.length; i++){
			question_list.put(QuestionStorage.getInstance().get(question_ids[i]).toJSON());
		}
		JSONObject resp = new JSONObject();
		resp.put("questions", question_list);
		return resp;

	}

}
