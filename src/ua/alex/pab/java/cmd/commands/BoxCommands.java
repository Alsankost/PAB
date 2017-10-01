package ua.alex.pab.java.cmd.commands;

import ua.alex.pab.java.cmd.CommandLoader;
import ua.alex.pab.java.cmd.CommandObserver;

public class BoxCommands implements CommandLoader {

	@Override
	public String getNamePack() {
		return "Box 0.0.1";
	}

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public String getName(int index) {
		switch (index) {
			case 0:  return "привет";
			case 1:  return "гори";
			case 2:  return "зови-меня";
			case 3:  return "мои-права";
			case 4:  return "команды";
			case 5:  return "bash";
			default: return null;
		}
	}

	@Override
	public CommandObserver getCommandObserver(int index) {
		switch (index) {
			case 0:  return new       Hello_Command();
			case 1:  return new        Fire_Command();
			case 2:  return new     SetName_Command();
			case 3:  return new      MyLaws_Command();
			case 4:  return new CommandList_Command();
			case 5:  return new  ServerBash_Command();
			default: return null;
		}
	}

	@Override
	public String getLaw(int index) {
		switch (index) {
			case 0:  return "public";
			case 1:  return "public";
			case 2:  return "public";
			case 3:  return "public";
			case 4:  return "public";
			case 5:  return "root";
			default: return null;
		}
	}

	@Override
	public String getMan(int index) {
		switch (index) {
			case 0:  return null;
			case 1:  return "вызовете что бы бот горел!";
			case 2:  return "вызовите с указанием вашего новго имени";
			case 3:  return "выводит список ваших прав";
			case 4:  return "выводит список команд";
			default: return null;
		}
	}
	
}