package ua.alex.pab.java;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.base.BotNickSpace;
import ua.alex.pab.java.cmd.Commands;
import ua.alex.pab.java.cmd.commands.BoxCommands;
import ua.alex.pab.java.data.DataManager;

public abstract class BotInf extends TelegramLongPollingBot {
	protected BotNickSpace botNickSpace;
	protected DataManager  dataManager;
	protected Commands     commands;
	
	public BotInf(String nick, DataManager dataManager) {
		if (dataManager == null) {
			throw new IllegalArgumentException();
		}
		
		this.dataManager  = dataManager;
		this.botNickSpace = new BotNickSpace(nick);
		this.commands     = new Commands(this);
		
		this.dataManager.loadLaws();
		this.commands.loadCommands(new BoxCommands());
	}
	
	public BotNickSpace getBotNickSpace() {
		return botNickSpace;
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}
	
	public Commands getCommands() {
		return commands;
	}
}
