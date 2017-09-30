package ua.alex.pab.java;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.base.BotNickSpace;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.Command;
import ua.alex.pab.java.cmd.Commands;
import ua.alex.pab.java.data.DataManager;


public class Bot extends TelegramLongPollingBot {

	//public static Pattern pattern = Pattern.compile("windows");
	
	private BotNickSpace botNicks;
	private DataManager dataManager;
	private Commands commands;
	
	public BotNickSpace getBotNicks() {
		return botNicks;
	}
	
	public DataManager getDataManager() {
		return dataManager;
	}
	
	public Bot(String nick, DataManager dm) {
		botNicks = new BotNickSpace(nick);
		dataManager = dm;
		commands = new Commands(dataManager);
		
		
	}
	
	@Override
	public String getBotUsername() {
		return "PowerAss";
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String messageText = update.getMessage().getText();
			long chatId = update.getMessage().getChatId();
	        String userName = update.getMessage().getFrom().getFirstName();
	        
	        User user = this.dataManager.getUserFromId(update.getMessage().getFrom().getId());
	        if (user == null) {
	        	user = this.dataManager.getDefaultUser(userName);
	        }
	        
	        String context = botNicks.parseRequest(messageText);
	        if (context != null) {
	        	if (context.length() == 0) {
	        		Command command = Commands.parseCommand(context);
	        		if (command != null) {
	        			this.sendTextMessageToChat(user.getName() + ", хз что это должно значить...", chatId);
	        			return;
	        		}
	        		
	        		String result = commands.executeCommand(command, user);
	        		if (result.compareTo("#ERROR:LAW") == 0) {
	        			this.sendTextMessageToChat(user.getName() + ", у вас недостаточно прав для этого действия!", chatId);
	        			return;
	        		}
	        		
	        		if (result.length() > 0) {
	        			this.sendTextMessageToChat(user.getName() + ", " + result, chatId);
	        		}
	        	}
	        }
	        
		}
	        /*
	        Matcher matcher = pattern.matcher(message_text);
	        boolean isW = matcher.find();
			
	        if (message_text.equals("Тут только один царь!@DovahZul!!!")) {
	        	SendMessage message = new SendMessage().setChatId(chat_id).setText("царь");
                
                try {
                	sendMessage(message);
                } catch (TelegramApiException e) {
                	e.printStackTrace();
                }
	        }
	        
			if (isW) {
				SendMessage message = new SendMessage().setChatId(chat_id).setText(userName + " ахуел совсем!? Не упонимай это говно в моем присутствии!");
				try {
					sendMessage(message);
                } catch (TelegramApiException e) {
                	e.printStackTrace();
                }
				
				message = new SendMessage().setChatId(chat_id).setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!");
				try {
					sendMessage(message);
                } catch (TelegramApiException e) {
                	e.printStackTrace();
                }
			}
						
			System.out.println(message_text);
			if (message_text.equals("Вадим рахит!")) {
                SendMessage message = new SendMessage().setChatId(chat_id).setText("Воистинну рахит!");
                
                try {
                	sendMessage(message);
                } catch (TelegramApiException e) {
                	e.printStackTrace();
                }
			}

        } */
		
	}
	
	private boolean sendTextMessageToChat(String text, long id) {
		SendMessage message = new SendMessage().setChatId(id).setText(text);
		
		try {
			sendMessage(message);
			return true;
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public String getBotToken() {
		return "456848733:AAHCDTuJUqksxykPK1-YV-9coniSaiIm1t4";
	}

}
