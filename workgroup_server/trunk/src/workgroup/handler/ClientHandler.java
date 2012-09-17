package workgroup.handler;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import workgroup.group.Member;
import workgroup.message.Message;

public class ClientHandler extends Thread {
	
	private static Logger log = Logger.getLogger(ClientHandler.class);
	 
	private Member member;
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in; 
	
	public ClientHandler(Member member, Socket clientSocket) throws IOException {
		super("Handler:" + member);
		this.member = member;
		this.socket = clientSocket;
		this.out = new ObjectOutputStream(clientSocket.getOutputStream());
		start();
	}
	
	public void send(Message message) {
		try {
			log.debug("Enviando mensagem para [" + getMember() + "]" );
			out.writeObject(message);
		} catch (IOException e) {
			log.error("Erro ao enviar mensagem");
		}
	}

	private void receive(Message message) {
		log.debug("Mensagem recebida");
		member.receive(message);
	}

	public Member getMember() {
		return member;
	}
	
	private void listenMessage() {
		try {
			in  = new ObjectInputStream(socket.getInputStream());
			while (true) {
				try {
					Message message = (Message) in.readObject();
					receive(message);
				} catch (ClassNotFoundException e) {
					log.warn("Erro não tratado: " + e.getMessage());
				}
			}

		} catch (EOFException e) {
			log.warn("Erro não tratado: " + e.getMessage());
			if (log.getEffectiveLevel() == Level.DEBUG) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			log.error("Erro de I/O, o socket será fechado: " + e.getMessage());
			if (log.getEffectiveLevel() == Level.DEBUG) {
				e.printStackTrace();
			}
		} finally {
			member.remove();
		}
	}

	public void run() {
		listenMessage();
	}
	
	public Socket getSocket() {
		return socket;
	}

}
