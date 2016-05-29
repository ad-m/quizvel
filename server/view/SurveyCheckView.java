package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

public class SurveyCheckView extends AbstractSessionAuthenticatedJSONView {
	public static final int COUNT = Config.QUESTION_COUNT;
	public static final String SEPERATOR = ";";
	public static final Random rand =  new Random();

	public static int[] convertIntegers(List<Integer> integers)
	{
	    int[] ret = new int[integers.size()];
	    Iterator<Integer> iterator = integers.iterator();
	    for (int i = 0; i < ret.length; i++)
	    {
	        ret[i] = iterator.next().intValue();
	    }
	    return ret;
	}
	private static int[] string_to_array(String numbers){
		List<Integer> list = new ArrayList<Integer>();
		for (String s : numbers.split(",")){
		    list.add(Integer.parseInt(s));
		};
		return convertIntegers(list);
	}
	
	@Override
	public JSONObject getJSON(Request request, User user, Session session) throws JSONException {
		String question_str = SessionStorage.getInstance().get(user).get("question");
		int[] question_ids = string_to_array(question_str);
		QuestionStorage qs = QuestionStorage.getInstance();
		int point = 0;

		JSONArray answers = request.getJSON().getJSONArray("answers");
		for(int i=0; i<question_ids.length; i++){
			if(qs.get(question_ids[i]).validate(answers.getInt(i))){
				point+=1;
			}
		}

		JSONObject resp = new JSONObject();
		resp.put("point", point);
		return resp;

	}

}