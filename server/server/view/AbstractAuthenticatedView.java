package server.view;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import core.http.HTTPObject;
import core.http.Request;
import core.http.Response;
import core.model.User;
import server.storage.UserStorage;

public abstract class AbstractAuthenticatedView extends AbstractView {
	public static final Pattern re_basic = Pattern.compile("^Basic (.+?)$");

	public HTTPObject getResponse(Request request) {
		User user = parse_authentication_header(request);
		if(user == null){
			return authenticationFail(request);		
		}

		return getResponse(request, user);
	}

	private User parse_authentication_header(Request request) {
		UserStorage users = UserStorage.getInstance();
		String authentication_string = request.get("authorization");
		if(authentication_string == null){
			return null;
		}
		Matcher matcher = AbstractAuthenticatedView.re_basic.matcher(authentication_string);
		if(!matcher.find()){
			return null;		
		}
		
		String encoded = new String(Base64.getDecoder().decode(matcher.group(1)));
		String[] parts = encoded.split(":", 2);
		if(parts.length < 2){
			return null;
		}
		return users.authenticate(parts[0], parts[1]);
	};

	public HTTPObject authenticationFail(Request request){
		Map<String, String> headers = new HashMap<>();
		headers.put("WWW-Authenticate", "Basic realm=\"Authentication required\"");
		return new Response("", 401, headers);
	}

	public abstract HTTPObject getResponse(Request request, User user);
	
}
