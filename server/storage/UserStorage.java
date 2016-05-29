package storage;

import java.util.Arrays;
import java.util.function.Predicate;

import model.User;


public class UserStorage extends AbstractStoreData<User>{
	public final static UserStorage INSTANCE = new UserStorage();
	
	private UserStorage() {
	}

	
	@Override
	public boolean add(User arg0) {
		if(findByUserName(arg0.getUsername())!=null){
			return false;
		}
		return super.add(arg0);
	}


	public static UserStorage getInstance() {
		return INSTANCE;
	}

	public User findByUserName(final String username){
		return this.first(new Predicate<User>() {
			
		    @Override
		    public boolean test(User t) {
		        return t.getUsername().equals(username);
		    }
		});
	}
	public User authenticate(String username, String password){
		User user = findByUserName(username);
		
		if(user != null && user.checkPassword(password) == true)
			return user;
		return null;		
	}
	public static void main(String[] args) {
		UserStorage store = new UserStorage();
		User u1 = new User("Alice", "pass");
		assert store.add(u1) == true;
		assert u1.equals(store.findByUserName("Alice"));
		assert Arrays.equals(new User[]{u1},store.toArray());
		assert store.size() == 1;
	}
}
