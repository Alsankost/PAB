package ua.alex.pab.java.cmd.commands;

import java.io.File;

import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class GetFile_Command implements CommandObserver {
	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		// TODO Auto-generated method stub
		try {
			if (args.length == 0) return "бля, укажи какой файл";
			File file = new File(args[0]);
	        SendDocument sd = new SendDocument();
	        sd.setChatId(update.getMessage().getChatId());
	        sd.setCaption(update.getMessage().getCaption());
			sd.setNewDocument(file);
			botProxy.sendDocument(sd);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ой, тут чет не так...";
		}
		return "воть";
	}
}
