package server.view;

import core.http.HTTPObject;
import core.http.Request;
import core.model.User;

public abstract class AbstractAdminJSONView extends AbstractAuthenticatedJSONView {

	@Override
	public HTTPObject getResponse(Request request, User user) {
		if(user.isAdmin() == false){
			return super.authenticationFail(request);
		}
		return getResponse(request, user);
	}


}
