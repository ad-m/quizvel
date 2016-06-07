package server.view.generic;

import core.http.Request;
import core.http.Response;
import core.model.User;

public abstract class AbstractAdminJSONView extends AbstractAuthenticatedJSONView {

	@Override
	public Response getResponse(Request request, User user) {
		if (user.isAdmin() == false) {
			return super.authenticationFail(request);
		}
		return super.getResponse(request, user);
	}

}
