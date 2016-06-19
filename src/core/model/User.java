package core.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends DataModel {
	private static final long serialVersionUID = -406316548387009043L;
	private String username;
	private byte[] hash;
	private boolean admin;

	public User(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public User(JSONObject obj) {
		this.username = obj.getString("username");
		if (obj.has("password")) {
			this.setPassword(obj.getString("password"));
		}
		if (obj.has("admin")) {
			this.admin = obj.getBoolean("admin");
		}
	};

	public boolean checkPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			return Arrays.equals(hash, this.hash);
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}

	public boolean setPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(password.getBytes());
			this.hash = md.digest();
			return true;
		} catch (NoSuchAlgorithmException e) {
			return false;
		}
	}

	public User(String username, String password) {
		this.username = username;
		this.setPassword(password);
		this.admin = false;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", is_admin=" + admin + "]";
	}

	public static void main(String[] args) {

		User u1 = new User("user1", "good");
		assert u1.checkPassword("good");
		assert !u1.checkPassword("bad");
		assert u1.setPassword("new");
		assert !u1.checkPassword("good");
		assert u1.checkPassword("new");

	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("username", this.username);
			json.put("admin", this.admin);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
