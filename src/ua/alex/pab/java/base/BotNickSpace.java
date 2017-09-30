package ua.alex.pab.java.base;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotNickSpace {
	private List<String> botNicks;
	
	private Pattern namePattern = Pattern.compile("^.+,");
	
	public BotNickSpace(String main) {
	
		if (main == null || main.length() == 0) {
			throw new IllegalArgumentException();
		}
		
		botNicks = new ArrayList<String>();
		botNicks.add(main.toUpperCase()); 
	}
	
	public void addNick(String nick) {
		if (nick == null || nick.length() == 0) {
			throw new IllegalArgumentException();
		}

		botNicks.add(nick.toUpperCase());
	}
	
	public String parseRequest(String request) {
		Matcher m = namePattern.matcher(request);
		boolean res = m.find();
		System.out.println(m.group());
		if (res && botNicks.indexOf(request.substring(m.start(), m.end() - 1).toUpperCase()) >= 0) {
			return request.substring(m.end(), request.length());
		}
		return null;
	}
}
