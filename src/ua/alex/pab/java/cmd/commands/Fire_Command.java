package ua.alex.pab.java.cmd.commands;

import java.util.Random;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class Fire_Command implements CommandObserver {

	public static Random random = new Random();
	
	@SuppressWarnings("deprecation")
	@Override
	public String execute(String[] args, BotInf botProxy, User usr, Update update) {
		SendMessage message = new SendMessage()
		.setChatId(update.getMessage().getChatId()).setText("АААААААААААААААААААААААААААААААААААААААААА!!!!");
		
		int count = random.nextInt(9) + 1;
		
		for (int i = 0; i < count; i++) {
			try {
				botProxy.sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
				return "";
			}
		}
		
		return "";
	}

}
