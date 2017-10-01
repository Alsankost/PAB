package ua.alex.pab.java.cmd.commands;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class MyLaws_Command implements CommandObserver {

	@Override
	public String execute(String[] args, BotInf botProxy, User usr, Update update) {
		String[] laws = usr.getLaws();
		String tmp = "ваши права:\n";
		for (int i = 0; i < laws.length; i++) {
			tmp += " -" + laws[i] + "\n";
		}
		return tmp;
	}
	
}
