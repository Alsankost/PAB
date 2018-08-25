package ua.alex.pab.java.cmd.commands;

import java.util.Random;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class Obosrat_Command implements CommandObserver {

	private static Random random = new Random();
	
	@SuppressWarnings("deprecation")
	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		if (args.length == 0) {
			return "нужно указать кого обосрать";
		}
		
		String result = args[0] + ", ";
		
		switch (random.nextInt(10)) {
			case 0:
				result += " ты ничтожество несчастное!";
				break;
			case 1:
				result += " сдохни пожалуйста!";
				break;
			case 2:
				result += " считай что ты обоссан!";
				break;
			case 3:
				result += " тебе не стать человеком, смирись!";
				break;
			case 4:
				result += " винда - это твой ближайший родственник!";
				break;
			case 5:
				result += " даже иван лучше тебя!";
			case 6:
				result += " так, я не понял, ты еще не сдох?";
				break;
			case 7:
				result += " чтоб кодил ты на 1С до конца жизни!!!";
				break;
			case 8:
				result += " я приду к тебе ночью! тебе не избежать этого! ухахааха!!!";
				break;
			case 9:
				result += " чем это вотняет?";
		}
		
		SendMessage message = new SendMessage()
		.setChatId(update.getMessage().getChatId()).setText(result);
		try {
			botProxy.sendMessage(message);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return " обосранно (с)";
	}
	
}

