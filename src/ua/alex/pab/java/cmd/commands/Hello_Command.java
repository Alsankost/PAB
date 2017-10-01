package ua.alex.pab.java.cmd.commands;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class Hello_Command implements CommandObserver {

	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		return "привет";
	}
	
}
