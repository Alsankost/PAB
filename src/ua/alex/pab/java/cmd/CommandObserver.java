package ua.alex.pab.java.cmd;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;

public interface CommandObserver {
	public String execute(String[] args, BotInf botProxy, User user, Update update);
}
