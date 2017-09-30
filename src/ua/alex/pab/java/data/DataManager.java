package ua.alex.pab.java.data;

import ua.alex.pab.java.base.Laws;
import ua.alex.pab.java.base.User;

public interface DataManager {
	public boolean loadLaws();
	public boolean saveLaws();
	
	public Laws getLaws();
	
	public boolean regUser(long id, String name);
	public boolean delUser(long id);
	
	public boolean addLawUser(long id, String law);
	public boolean delLawUser(long id, String low);
	
	public String  getUserProp(long id, String prop);
	public boolean setUserProp(long id, String prop, String value);
	
	public User getUserFromId(long id);
	public User getUserFromName(String name);
	public User getDefaultUser(String name);
}
