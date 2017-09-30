package ua.alex.pab.java.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ua.alex.pab.java.base.Law;
import ua.alex.pab.java.data.DataManager;

public class Commands {
	private Map<String,CommandPack> commands = new HashMap<String,CommandPack>();
	public DataManager botDataManager;
	
	private static final Pattern NAME_PATTERN  = Pattern.compile("^[\\S]+$");
	private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");
	private static final Pattern QUOTE_PATTERN = Pattern.compile("[^\\\\]\"");  
	
	public boolean regCommand(String name, CommandObserver co, String law) {
		Law lawObj = botDataManager.getLaws().getFromName(law);
		if (commands.containsKey(name) ||
			name == null || co == null ||
			law == null  || name.length() == 0 ||
			lawObj != null || !NAME_PATTERN.matcher(name).find()) {
			return false;
		}
		
		CommandPack tmp = new CommandPack(co, lawObj);
		commands.put(name, tmp);
		return true;
	}
	
	public static String[] parseCommand(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}
		String copyText = text += ' ';
		
		Matcher spaces = SPACE_PATTERN.matcher(copyText);
		spaces.find();
		
		String commandName = copyText.substring(0, spaces.start());
		if (!NAME_PATTERN.matcher(commandName).find()) {
			return null;
		}
		
		List<Integer> quotesList = new ArrayList<Integer>();
		Matcher quotes = QUOTE_PATTERN.matcher(copyText);
		while (quotes.find()) {
			quotesList.add(quotes.end());
		}
		if (quotesList.size() % 2 != 0) {
			return null;
		}
		/*
		for (int i = 0; i < quotesList.size(); i+=2) {
			System.out.println(copyText.substring(quotesList.get(i) - 1, quotesList.get(i + 1)));
		}*/
		
		List<String> argsList = new ArrayList<String>();
		argsList.add(commandName);
		int mem = spaces.end();
		while (spaces.find()) {
			boolean flag = false;
			//System.out.println("=================");
			for (int i = 0; i < quotesList.size() - 1 && quotesList.get(i) - 1 < spaces.start(); i+=2) {
				//System.out.println(spaces.start() + " " + quotesList.get(i) + " " + quotesList.get(i + 1));
				if (quotesList.get(i) - 1 < spaces.start() && quotesList.get(i + 1) > spaces.start()) {
					flag = true;
				}
			}
			if (flag) {
				continue;
			}
			
			argsList.add(copyText.substring(mem, spaces.end() - 1));
			mem = spaces.end();
		}
		
		String[] tmp = new String[argsList.size()];
		argsList.toArray(tmp);
		return tmp;
	}
	
	public String executeCommand();
	
	
}

class CommandPack {
	public CommandObserver commandObserver;
	public Law law;
	
	public boolean ignore;
	
	public CommandPack(CommandObserver co, Law law) {
		this.commandObserver = co;
		this.law = law;
		
		this.ignore = false;
	}
}