package ua.alex.pab.java.cmd.commands;

import org.telegram.telegrambots.api.objects.Update;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class SetName_Command implements CommandObserver {

	@Override
	public String execute(String[] args, BotInf bot, User usr, Update update) {
		String tmp = "";
		for (int i = 0; i < args.length; i++) {
			tmp += args[i] + ( (i < args.length - 1)?" ":"" );
		}
		
		if (tmp.length() < 2) {
			return "псевдоним должен сожержать 2 и более символов";
		}
		
		if (bot.getDataManager().getUserFromId(usr.getId()) == null) {
			bot.getDataManager().regUser(usr.getId(), tmp);
		}
		bot.getDataManager().setUserProp(usr.getId(), "name", tmp);
		
		return "ваш псевдоним успешно обновлен";
	}

}
