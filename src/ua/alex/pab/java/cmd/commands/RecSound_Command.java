package ua.alex.pab.java.cmd.commands;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import ua.alex.pab.java.BotInf;
import ua.alex.pab.java.base.User;
import ua.alex.pab.java.cmd.CommandObserver;

public class RecSound_Command implements CommandObserver {
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");

	@SuppressWarnings("static-access")
	@Override
	public String execute(String[] args, BotInf botProxy, User user, Update update) {
		if (args.length != 1) return "необходимо указать сколько секунд производить запись!";
		int time = 0;
		try {
			time = Integer.parseInt(args[0]);
		}
		catch (Exception ex) {
			return "неверно указано время записи!";
		}

		try {
			File outputFile = new File("./" + formatter.format(Calendar.getInstance().getTime()) + ".wav");  

			AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100.0F, 16, 2, 4, 44100.0F, false);  
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);  
			TargetDataLine targetDataLine = null;  
			try {  
				targetDataLine = (TargetDataLine) AudioSystem.getLine(info);  
				targetDataLine.open(audioFormat);  
			} catch (LineUnavailableException e) {   
				e.printStackTrace();
				return "#ERROR:EXCEPTION";
			}  

			AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
			TestSound j = new TestSound(targetDataLine,targetType,outputFile);  
			
			final int ftime = time;
			
			@SuppressWarnings("deprecation")
			Thread thr = new Thread(() -> {
				j.start();
				try {
					Thread.currentThread().sleep(ftime * 1000);
				} catch (InterruptedException e) {
					SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId()).setText("Запись аварийно остановенна!");
					try {
						botProxy.sendMessage(message);
					} catch (TelegramApiException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				j.stopRecording();
				SendAudio sound = new SendAudio().setChatId(update.getMessage().getChatId()).setNewAudio(outputFile);
				try {
					botProxy.sendAudio(sound);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				outputFile.delete();
			});
			thr.start();
			
			return "производится запись! ожидайте!";
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return "#ERROR:EXCEPTION";
		}
		//return "вот хз почему, но произошла какая-то ху**я в этой команде, прости...";
	}
}

class TestSound extends Thread {  
	private TargetDataLine       m_line;  
	private AudioFileFormat.Type m_targetType;  
	private AudioInputStream     m_audioInputStream;  
	private File                 m_outputFile;  

	public TestSound(TargetDataLine m_line, AudioFileFormat.Type m_targetType,  File m_outputFile) {  
		this.m_line = m_line;  
		this.m_targetType = m_targetType;  
		this.m_audioInputStream = new AudioInputStream(m_line);  
		this.m_outputFile = m_outputFile;  
	}  

	public void start() {  
		m_line.start();  
		super.start();  
	}  

	public void stopRecording() {  
		m_line.stop();  
		m_line.close();  
	}  


	public void run() {  
		try { 
			AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}    
}  