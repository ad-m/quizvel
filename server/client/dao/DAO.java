package client.dao;

import java.io.IOException;
import java.net.UnknownHostException;

import core.http.Request;
import core.http.Response;

public class DAO extends AbstractDAO {

	private User user;

	public boolean authenticate(User user){
		Response resp;
		try {
			System.err.println("Started authenticate");
			resp = this.send_request(new Request("/user/~current", "GET", ""), user);
			if(resp.getStatus() == 200){
				this.user = user;
				return true;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		DAO dao = new DAO();
		System.out.print(dao.authenticate(new User("fail", "fail")));
		System.out.print(dao.authenticate(new User("xyz", "xyz")));
	}

};
