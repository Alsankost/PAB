package ua.alex.pab.java.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class Users {
	private Map<Long,User> userMap;
	
	@SuppressWarnings("unused")
	private String[] defaultLaws;
	
	class EntityUser extends User {
		public EntityUser(long id, String name) {
			this.id = id;
			this.name = name;
		}
	}
	
	public Users(String[] laws) {
		defaultLaws = laws;
		userMap = new HashMap<Long,User>();
	}
	
	public boolean regName(long id, String name) {
		List<User> users = new ArrayList<User>(userMap.values());
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().compareTo(name) == 0) {
				return false;
			}
		}
		User tmp = new EntityUser(id,name);
		userMap.put(id,tmp);
		return true;
	}
}
