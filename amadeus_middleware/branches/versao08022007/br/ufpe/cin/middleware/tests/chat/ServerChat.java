package br.ufpe.cin.middleware.tests.chat;

import java.util.Vector;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.util.Debug;

public class ServerChat {

	private AmadeusMiddleware middleware;
	@SuppressWarnings("unused")
	private Vector<ChatUser> users;
	
	public ServerChat() {
		users = new Vector<ChatUser>();
		middleware = new AmadeusMiddleware();
		middleware.open();
	}
	
	public static void main(String[] args) {
		ServerChat server = new ServerChat();
		while(true) {
			Message me = null;
			while(me == null) {
				try {
					me = (Message) server.middleware.receive();
				} catch (NullPointerException e) {
					Debug.printStack(e);
				} catch (NotConnectedException e) {
					Debug.printStack(e);
				}
			}
			
			//fazer o sendToAll
		}
	}
	
}
