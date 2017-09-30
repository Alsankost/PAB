package ua.alex.pab.java.cmd;

import ua.alex.pab.java.BotInf;

public interface CommandObserver {
	public String execute(String[] args, BotInf botProxy);
}
