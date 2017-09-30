package ua.alex.pab.java.cmd;

import java.util.ArrayList;
import java.util.List;

public class Command {
	private String nameCommand;
	private List<String> arguments;
	
	public Command(String name) {
		nameCommand = name;
		arguments = new ArrayList<String>();
	}
	
	public Command(String name, List<String> args) {
		nameCommand = name;
		arguments = args;
	}
	
	public String[] getArguments() {
		String[] tmp = new String[arguments.size()];
		arguments.toArray(tmp);
		return tmp;
	}
	
	public int getArgumnetsCount() {
		return arguments.size();
	}
	
	public String getArgument(int i) {
		return arguments.get(i);
	}
	
	public String getName() {
		return nameCommand;
	}
	
	public boolean addArgumnt(String arg) {
		return arguments.add(arg);
	}
	
	public void addArgumnt(int i, String arg) {
		arguments.add(i, arg);
	}
}
