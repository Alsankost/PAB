package ua.alex.pab.java.cmd.commands;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class CommandList_Command implements CommandObserver {

	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		String[] commands = botProxy.getCommands().getCommandNames();
		String result = "\n";
		for (int i = 0; i < commands.length; i++) {
			String man = botProxy.getCommands().getManual(commands[i]);
			result += "-**" + commands[i] + "**" +
					( (man != null && man.length() > 0)?" - "+man:"") + "\n";
		}
		return result;
	}

}
