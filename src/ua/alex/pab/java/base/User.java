package ua.alex.pab.java.base;

public abstract class User {
	protected long id;
	protected String name;
	protected String[] laws;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getLaws() {
		return laws.clone();
	}
}
