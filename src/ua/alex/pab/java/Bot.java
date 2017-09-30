package ua.alex.pab.java;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.Command;
import ua.alex.pab.java.cmd.Commands;
import ua.alex.pab.java.data.DataManager;


public class Bot extends BotInf {

	//public static Pattern pattern = Pattern.compile("windows");
	public Bot(String nick, DataManager dm) {
		super(nick, dm);
		dm.loadLaws();
		
		dm.regUser(427529611, "Батя");
		dm.regUser(294112796, "Пуканный звездолет");
		
		System.out.println((commands.regCommand("привет", (args, bot) -> { return "привет"; }, "public"))?"привет is load":"привет is not load");
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
	        
	        System.out.println("\n====MESSAGE====");
	        System.out.println("User: '" + user.getName() + "@" + update.getMessage().getFrom().getId() + "'");
	        System.out.println("Message: " + messageText);
	        
	        String context = botNickSpace.parseRequest(messageText);
	        if (context != null) {
	        	if (context.length() != 0) {
	        		System.out.println("Context: " + context);
	        		Command command = Commands.parseCommand(context);
	        		if (command == null) {
	        			System.out.println("#NO COMMAND");
	        			this.sendTextMessageToChat(user.getName() + ", хз что это должно значить...", chatId);
	        			return;
	        		}
	        		System.out.println("Command: " + command.getName());
	        		
	        		String result = commands.executeCommand(command, user);
	        		
	        		if (result == null) {
	        			System.out.println("#COMMAND IS NOT FOUND");
	        			this.sendTextMessageToChat(user.getName() + ", не знаю такой комманды...", chatId);
	        			return;
	        		}
	        		
	        		if (result.compareTo("#ERROR:EXCEPTION") == 0) {
	        			System.out.println("#EXECUTE ERROR");
	        			this.sendTextMessageToChat(user.getName() + ", с данной коммандой какие-то неполадки!", chatId);
	        			return;
	        		}
	        		
	        		if (result.compareTo("#ERROR:LAW") == 0) {
	        			System.out.println("#NO LAW FROM USER FOM CALL THIS COMMAND");
	        			this.sendTextMessageToChat(user.getName() + ", у вас недостаточно прав для этого действия!", chatId);
	        			return;
	        		}
	        		
	        		if (result.length() > 0) {
	        			System.out.println("Result: " + result);
	        			this.sendTextMessageToChat(user.getName() + ", " + result, chatId);
	        		}
	        		else {
	        			System.out.println("#NO RESULT");
	        		}
	        		return;
	        	}
	        }
	        System.out.println("#NO CONTEXT");
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
	
	@SuppressWarnings("deprecation")
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
