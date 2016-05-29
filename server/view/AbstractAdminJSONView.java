package view;

import http.Request;
import http.Response;
import model.User;

public abstract class AbstractAdminJSONView extends AbstractAuthenticatedJSONView {

	@Override
	public Response getResponse(Request request, User user) {
		if(user.isAdmin() == false){
			return super.authenticationFail(request);
		}
		return getResponse(request, user);
	}


}
