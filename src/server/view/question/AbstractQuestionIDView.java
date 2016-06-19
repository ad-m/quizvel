package server.view.question;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import server.view.generic.AbstractAdminJSONView;

public abstract class AbstractQuestionIDView extends AbstractAdminJSONView {

	public static final Pattern RE_PARSE_URL = Pattern.compile("/([0-9]+?)$");

	protected static Integer getID(String url) {
		Matcher matcher = RE_PARSE_URL.matcher(url);
		matcher.find();
		return Integer.valueOf(matcher.group(1));
	}

}