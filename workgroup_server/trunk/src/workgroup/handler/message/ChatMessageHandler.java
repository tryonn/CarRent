package workgroup.handler.message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;

import workgroup.group.GroupManager;
import workgroup.group.Member;
import workgroup.message.ChatMessage;
import workgroup.message.Message;
import workgroup.server.ServerModel;

public class ChatMessageHandler extends MessageHandler {

	private PrintStream logFile;
	private String userDir; 
	private String fileSeparator;
	private ServerModel serverModel;

	public ChatMessageHandler() {
		try {
			userDir = System.getProperty("user.dir");
			File file = File.createTempFile("chat", ".log", new File(userDir));
			System.out.println(file);
			logFile = new PrintStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			serverModel.getLogger().error("Erro ao iniciar log de chat", e);
		} catch (IOException e) {
			serverModel.getLogger().error("Erro ao iniciar log de chat", e);
		}
	}
	
	public void process(GroupManager groupManager, Member member, Message message) {
		if (message instanceof ChatMessage) {
			String texto = (String) message.getObject();
			String msg = "[" + member.getUser() + "] " + texto;
			ChatMessage messageToSend = new ChatMessage(msg);
			gravaLog(DateFormat.getDateTimeInstance().format(message.getReciveTime()) + msg);
			sendToAll(groupManager, member, messageToSend);
		} else {
			groupManager.getLogger().debug("<ChatMessageHandler> Message recebida não é ChatMessage");
		}
	}
	
	private void gravaLog(String message) {
		logFile.println(message);
	}

}
