package ua.alex.pab.java.cmd.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class ServerBash_Command implements CommandObserver {

	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		String command = "";
		for (int i = 0; i < args.length; i++) {
			command += args[i] + ( (i < args.length - 1)?" ":"" );
		}
		
		Thread thread = new Thread(new CommandThread(botProxy, update, command));
		thread.start();
		return "отправленно на выполнение";
	}

}

class CommandThread implements Runnable {

	BotInf bot;
	Update update;
	String command;
	
	public CommandThread(BotInf botProxy, Update update, String command) {
		bot          = botProxy;
		this.update  = update;
		this.command = command;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Runtime rt = Runtime.getRuntime();
	    try {
	    	Process proc = rt.exec(command);
			proc.waitFor();
			
			BufferedReader is = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line;
			
			String result = "";
			while ((line = is.readLine()) != null) {
				result += line + "\n";
			}
			SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId()).setText(result);
			
			try {
				bot.sendMessage(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
