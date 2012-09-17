package workgroup.group;

import java.io.IOException;
import java.net.Socket;

import workgroup.handler.ClientHandler;
import workgroup.message.Message;

public class Member {

	private User user;
	private GroupManager manager;
	private ClientHandler clientHandler;
	
	public Member(GroupManager manager, User user, Socket clientSocket) throws IOException {
		this.user = user;	
		this.manager = manager;
		this.clientHandler = new ClientHandler(this, clientSocket);  
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString() {
	 	return "Usuário: " + getUser();
	}

/*	
	public int compareTo(Object member) {
		return user.compareToIgnoreCase(((Member) member).getUser());
	}
*/
	/**
	 * @param handler Handler do cliente que enviou a mensagem
	 * @param message mensagem enviada
	 */
	public void receive(Message message) {
		manager.receive(this, message);
	}

	public void remove() {
//		log.debug("Fechando Socket...");
		try {
			clientHandler.getSocket().close();
//			log.debug("Socket Fechado");
		} catch( IOException e ) {
//			log.warn("Erro fechando socket", e);
		}
//		log.debug("Cliente removido");
		manager.remove(this);
	}

	/**
	 * @param message
	 */
	public void send(Member member, Message message) {
		clientHandler.send(message);
	}

	/**
	 * @param message
	 */
	public void send(Message message) {
		clientHandler.send(message);
	}
}
