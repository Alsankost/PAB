package ua.alex.pab.java.cmd.commands;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class ScreenShut_Command implements CommandObserver {
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
	
	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		Calendar now = Calendar.getInstance();
        Robot robot = null;
        File file = null;
		try {
			robot = new Robot();
			BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			file = new File("./"+formatter.format(now.getTime())+".png");
	        ImageIO.write(screenShot, "PNG", file);
	        
	        SendDocument sd = new SendDocument();
	        sd.setChatId(update.getMessage().getChatId());
	        sd.setCaption(update.getMessage().getCaption());
			sd.setNewDocument(file);
			botProxy.sendDocument(sd);
			//botProxy.send
			file.delete();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ой, тут чет не так...";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ой, тут чет не так...";
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ой, тут чет не так...";
		}
		return "воть";
	}

}
