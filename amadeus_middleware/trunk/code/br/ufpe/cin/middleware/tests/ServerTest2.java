package br.ufpe.cin.middleware.tests;

import br.ufpe.cin.middleware.exceptions.NotConnectedException;
import br.ufpe.cin.middleware.objects.Message;
import br.ufpe.cin.middleware.principal.AmadeusMiddleware;
import br.ufpe.cin.middleware.util.Debug;




public class ServerTest2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AmadeusMiddleware middleware = new AmadeusMiddleware();
		middleware.open();
		System.out.println("Esperando mensagem");
		//ServerListen server = new ServerListen(servidor);
		//server.start();
		while(true) {
			Message mensagem;
			try {
				do {
					mensagem = (Message) middleware.receive();
				} while(mensagem == null);
				System.out.println("Mensagem recebida (decriptada): " + mensagem.getContent());
			} catch (NullPointerException e) {
				Debug.printStack(e);
			} catch (NotConnectedException e) {
				Debug.printStack(e);
			}
		}

	}
	class ServerListen extends Thread {
//		ServerTCP servidor = null;

//		public ServerListen(ServerTCP servidor) {
//		super();
//		this.servidor = servidor;
//		}

//		public void run() {
//		try {
//		while(true) {
//		servidor.listen();
//		}
//		} catch (IOException e) {
//		Debug.printStack(e);
//		} catch (ClassNotFoundException e) {
//		Debug.printStack(e);
//		}

//		}
	}
}

