package workgroup.handler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import workgroup.exception.LoginException;
import workgroup.message.ControlMessage;
import workgroup.message.ErrorMessage;
import workgroup.server.PlattusServer;

public class LoginHandler extends Thread {

	private static Logger log = Logger.getLogger(LoginHandler.class);
	
	private Socket clientSocket;
	private PlattusServer server;
	
	public LoginHandler(PlattusServer server, Socket clienteSocket){
		super("LoginHandler");
		this.clientSocket = clienteSocket;
		this.server = server;
		start();
	}
	
	
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		try {
			// Lê messagem de controle para login
			in = new ObjectInputStream(clientSocket.getInputStream());
			ControlMessage message = (ControlMessage) in.readObject();
			String aplic = (String) message.getParameter(0);
			String grupo = (String) message.getParameter(1);
			String login = (String) message.getParameter(2);
			String senha = (String) message.getParameter(3);
			
			// Realiza login e adiciona cliente ao servidor
			processaLogin(aplic, grupo, login, senha);
			log.debug("Login realizado com sucesso");
			server.addCliente(grupo, login, clientSocket);
			
		} catch (LoginException e) {
			try {
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				out.writeObject(new ErrorMessage(e.getMessage()));
				out.close();
				clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				log.debug("Erro ao realizar login", e);
				out = new ObjectOutputStream(clientSocket.getOutputStream());
				out.writeObject(new ErrorMessage("Erro ao realizar login"));
				out.close();
				clientSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	private void processaLogin(String aplic, String grupo, String login, String senha) throws LoginException {
		if (login == null) {
			throw new LoginException();
		}
	}

}
