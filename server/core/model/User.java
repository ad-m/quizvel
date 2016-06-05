package core.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends DataModel{
	private static final long serialVersionUID = -406316548387009043L;
	private String username;
	private byte[] hash;
	private boolean admin;

	public String getUsername() {
		return username;
	}

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
			MessageDigest md= MessageDigest.getInstance("SHA");
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
		assert u1.checkPassword("good") == true;
		assert u1.checkPassword("bad") == false;
		assert u1.setPassword("new") == true;
		assert u1.checkPassword("good") == false;
		assert u1.checkPassword("new") == true;
		
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

	@Override
	public DataModel fromJSON(JSONObject obj) {
		return new User(obj.getString("username"), obj.getString("password"));
	}
}
