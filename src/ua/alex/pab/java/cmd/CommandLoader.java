package ua.alex.pab.java.cmd;

public interface CommandLoader {
	public String getNamePack();
	
	public int getCount();
	
	public String          getName(int index);
	public CommandObserver getCommandObserver(int index);
	public String		   getLaw(int index);
	public String		   getMan(int index);
}
