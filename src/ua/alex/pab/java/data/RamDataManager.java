package ua.alex.pab.java.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.alex.pab.java.base.Laws;
import ua.alex.pab.java.base.User;

public class RamDataManager implements DataManager {

	private Laws laws = null;
	private String[] defaultLaws = {"public"};
	
	@Override
	public boolean loadLaws() {
		laws = new Laws();
		laws.addLaw("public", "root");
		return true;
	}

	@Override
	public boolean saveLaws() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Laws getLaws() {
		// TODO Auto-generated method stub
		return laws;
	}

	class EntityUser extends User {
		public EntityUser(long id, String name) {
			this.id   = id;
			this.name = name;
			this.laws = defaultLaws.clone();
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public boolean isAllowLaw(String law) {
			for (int i = 0; i < this.laws.length; i++) {
				if (this.laws[i].compareTo(law) == 0) {
					return true;
				}
			}
			return false;
		}
		
		public void addLaw(String law) {
			String[] tmp = new String[this.laws.length + 1];
			for (int i = 0; i < this.laws.length; i++) {
				tmp[i] = this.laws[i];
			}
			tmp[tmp.length - 1] = law;
		}
		
		public void delLaw(String law) {
			String[] tmp = new String[this.laws.length - 1];
			for (int i = 0; i < this.laws.length; i++) {
				if (tmp[i].compareTo(law) != 0) {
					tmp[i] = this.laws[i];
				}
			}
		}
	}
	
	private Map<Long, EntityUser> users;
	
	public RamDataManager() {
		users = new HashMap<Long,EntityUser>();
	}
	
	
	@Override
	public boolean regUser(long id, String name) {
		if (users.containsKey(id)) {
			return false;
		}
		
		List<User> vals = new ArrayList<User>(users.values());
		for (int i = 0; i < vals.size(); i++) {
			if (vals.get(i).getName().compareTo(name) == 0) {
				return false;
			}
		}
		
		users.put(id, new EntityUser(id,name));
		return true;
	}

	@Override
	public boolean delUser(long id) {
		return users.remove(id) != null;
	}

	@Override
	public boolean addLawUser(long id, String law) {
		EntityUser tmp = users.get(id);
		if (tmp == null || tmp.isAllowLaw(law)) {
			return false;
		}
		tmp.addLaw(law);
		return true;
	}

	@Override
	public boolean delLawUser(long id, String law) {
		EntityUser tmp = users.get(id);
		if (tmp == null || !tmp.isAllowLaw(law)) {
			return false;
		}
		tmp.delLaw(law);
		return true;
	}

	@Override
	public User getUserFromId(long id) {
		return users.get(id);
	}

	@Override
	public User getUserFromName(String name) {
		List<User> tms = new ArrayList<User>(users.values());
		for (int i = 0; i < tms.size(); i++) {
			User tmp = tms.get(i);
			if (tmp.getName().compareTo(name) == 0) {
				return tmp;
			}
		}
		return null;
	}

	@Override
	public String[] getDefaultLaws() {
		return defaultLaws;
	}

	@Override
	public String getUserProp(long id, String prop) {
		User tmp = users.get(id);
		
		if (tmp == null) {
			return null;
		}
		
		switch (prop) {
			case "name":
				return tmp.getName();
			default:
				return null;
		}
	}

	@Override
	public boolean setUserProp(long id, String prop, String value) {
		EntityUser tmp = users.get(id);
		
		if (tmp == null || value == null) {
			return false;
		}
		
		switch (prop) {
			case "name":
				if (value.length() == 3) {
					return false;
				}
				tmp.setName(value);
				return true;
			default:
				return false;
		}
	}
}
