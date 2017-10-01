package ua.alex.pab.java.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.Law;
import ua.alex.pab.java.base.User;

public class Commands {
	private Map<String,CommandPack> commands;
	private BotInf botProxy;
	
	private static final Pattern NAME_PATTERN  = Pattern.compile("^\\S+$");
	private static final Pattern SPACE_PATTERN = Pattern.compile("\\s+");
	private static final Pattern QUOTE_PATTERN = Pattern.compile("[^\\\\]\"");
	private static final Pattern HEAD_SPACE_PATTERN = Pattern.compile("^\\s+");
	
	public Commands(BotInf botProxy) {
		this.botProxy = botProxy;
		commands = new HashMap<String,CommandPack>();
	}
	
	//Static methods:
	public static boolean isValideCommandName(String name) {
		return NAME_PATTERN.matcher(name).find();
	}
	
	public static String normName(String name) {
		return (name == null || name.length() == 0)?"unknow":name;
	}
	
	public static Command parseCommand(String text) {
		if (text == null || text.length() == 0) {
			return null;
		}
		
		String copyText = text += ' ';
		Matcher headSpaces = HEAD_SPACE_PATTERN.matcher(copyText);
		if (headSpaces.find()) {
			copyText = copyText.substring(headSpaces.end(), copyText.length());
		}
		
		Matcher spaces = SPACE_PATTERN.matcher(copyText);
		spaces.find();
		
		String commandName = copyText.substring(0, spaces.start());
		if (!isValideCommandName(commandName)) {
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
		
		List<String> argsList = new ArrayList<String>();
		int mem = spaces.end();
		while (spaces.find()) {
			boolean flag = false;
			for (int i = 0; i < quotesList.size() - 1 && quotesList.get(i) - 1 < spaces.start(); i+=2) {
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
		
		return new Command(commandName, argsList);
	}
	
	//Object methods:
	public boolean regCommand(String name, CommandObserver co, String law) {
		Law lawObj = botProxy.getDataManager().getLaws().getFromName(law);
		if (commands.containsKey(name) ||
			name == null || co == null ||
			law == null  || name.length() == 0 ||
			lawObj == null || !NAME_PATTERN.matcher(name).find()) {
			return false;
		}
		
		CommandPack tmp = new CommandPack(co, lawObj);
		commands.put(name, tmp);
		return true;
	}
	
	public void loadCommands(CommandLoader loader) {
		if (loader == null) {
			return;
		}
		
		String packName = loader.getNamePack();
		System.out.println("Loading " + normName(packName) + " command package");
		
		int count = loader.getCount();
		for (int i = 0; i < count; i++) {
			String name = loader.getName(i);
			System.out.print(" -" + normName(name) + ":");
			if (!isValideCommandName(name)) {
				System.out.println("\n   FAIL: invalid command name");
				continue;
			}
			
			if (commands.containsKey(name)) {
				System.out.println("\n   FAIL: this command name in use");
				continue;
			}
			
			CommandObserver observer = loader.getCommandObserver(i);
			if (observer == null) {
				System.out.println("\n   FAIL: command observer is not exist");
				continue;
			}
			
			boolean noWarning = true;
			
			Law law = botProxy.getDataManager().getLaws().getFromName(loader.getLaw(i));
			if (law == null) {
				law = botProxy.getDataManager().getLaws().getRoot();
				System.out.println("\n   WARNING: command law is not exist, 'root' selected");
				noWarning = false;
			}
			
			CommandPack tmp = new CommandPack(observer, law);
			
			String man = loader.getMan(i);
			if (normName(man).compareTo("unknow") == 0) {
				man = null;
				System.out.println("\n   WARNING: command manual is not exist");
				noWarning = false;
			}
			
			if (noWarning) {
				System.out.println("\n   OK");
			}
			
			tmp.manual = man;
			commands.put(name, tmp);
		}
	}
	
	public void setIgnore(String commandName, boolean value) {
		if (commandName == null || commandName.length() == 0) {
			return;
		}
		
		CommandPack tmp = commands.get(commandName);
		if (tmp == null) {
			return;
		}
		tmp.ignore = value;
	}
	
	public boolean isIgnore(String commandName) {
		if (commandName == null || commandName.length() == 0) {
			return true;
		}
		
		CommandPack tmp = commands.get(commandName);
		if (tmp == null) {
			return true;
		}
		return tmp.ignore;
	}
	
	public String executeCommand(Command com, User user, Update update) {
		if (com == null || user == null) {
			return null;
		}
		
		CommandPack tmp = commands.get(com.getName());
		if (tmp == null) {
			return null;
		}
		
		if (!tmp.law.inBranches(user.getLaws())) {
			return "#ERROR:LAW";
		}
		
		if (tmp.ignore) {
			return "#ERROR:IGNORE";
		}
		
		try {
			return tmp.commandObserver.execute(com.getArguments(), botProxy, user, update);
		}
		catch (Exception ex) {
			//LOG
			return "#ERROR:EXCEPTION";
			//System.out.println("!ERROR IN COMMAND!\n" + ex.getMessage());
		}
	}
	
	public String[] getCommandNames() {
		String[] tmp = new String[commands.keySet().size()];
		commands.keySet().toArray(tmp);
		return tmp;
	}
	
	public String getManual(String command) {
		return commands.get(command).manual;
	}
}

class CommandPack {
	public CommandObserver commandObserver;
	public Law law;
	public String manual;
	
	public boolean ignore;
	
	public CommandPack(CommandObserver co, Law law) {
		this.commandObserver = co;
		this.law = law;
		
		this.ignore = false;
	}
}