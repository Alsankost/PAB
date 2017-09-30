package ua.alex.pab.java;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.cmd.CommandObserver;
import ua.alex.pab.java.cmd.Commands;
import ua.alex.pab.java.data.RamDataManager;

public class Start {

	public static void main(String[] args) {
		/*
		ApiContextInitializer.init();

        // Instantiate Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi();
        
        Bot bot = new Bot("Пукан", new RamDataManager());
        bot.getBotNicks().addNick("бот");
        
        // Register our bot
        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
		*/
		RamDataManager rdm = new RamDataManager();
		rdm.loadLaws();
		Commands com = new Commands();
		com.botDataManager = rdm;
		CommandObserver co = (e) -> { return null; };
		com.regCommand("call", co, "root");
		String[] mas = com.parseCommand("hs \"asd\" assd, \" asdsda sdad \" asda ddd");
		for (int i = 0; i < mas.length; i++) {
			System.out.println(mas[i]);
		}
	}

}
