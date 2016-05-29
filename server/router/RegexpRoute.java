package router;

import http.Request;
import http.Request.Method;

import java.util.regex.Pattern;

import view.AbstractView;

public class RegexpRoute implements Route {
	private Method method;
	private Pattern pattern;
	private AbstractView view;

	public RegexpRoute(Method method, Pattern pattern, AbstractView view){
		this.method = method;
		this.pattern = pattern;
		this.view = view;
	}
	public RegexpRoute(Method method, String pattern, AbstractView view){
		this.method = method;
		this.pattern = Pattern.compile(pattern);
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see server.Route#match(java.lang.String)
	 */
	@Override
	public boolean match(Request request){
		if(!this.method.equals(request.getMethod())){
			return false;
		};
		return pattern.matcher(request.getUrl()).find();
	}
	/* (non-Javadoc)
	 * @see server.Route#getView()
	 */
	@Override
	public AbstractView getView() {
		return view;
	}
}